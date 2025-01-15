- __Emitting and Listening to Events__: A component can emit custom events directly in template expressions (e.g. in a v-on handler) using the built-in $emit method:
```
<!-- MyComponent -->
<button @click="$emit('someEvent')">Click Me</button>
```

- The parent can then listen to it using v-on:
```
<MyComponent @some-event="callback" />
```

- The .once modifier is also supported on component event listeners:
```
<MyComponent @some-event.once="callback" />
```

- Like components and props, event names provide an automatic case transformation. Notice we emitted a camelCase event, but can listen for it using a kebab-cased listener in the parent. As with props casing, we recommend using kebab-cased event listeners in templates.

- Unlike native DOM events, component emitted events do not bubble. You can only listen to the events emitted by a direct child component. If there is a need to communicate between sibling or deeply nested components, use an external event bus or a global state management solution.

- __Event Arguments__: It's sometimes useful to emit a specific value with an event. For example, we may want the `<BlogPost>` component to be in charge of how much to enlarge the text by. In those cases, we can pass extra arguments to $emit to provide this value:
```
<button @click="$emit('increaseBy', 1)">
  Increase by 1
</button>
```

- Then, when we listen to the event in the parent, we can use an inline arrow function as the listener, which allows us to access the event argument:
```
<MyButton @increase-by="(n) => count += n" />
```

- Or, if the event handler is a method:
```
<MyButton @increase-by="increaseCount" />
```

- Then the value will be passed as the first parameter of that method:
```
function increaseCount(n) {
  count.value += n
}
```

- All extra arguments passed to $emit() after the event name will be forwarded to the listener. For example, with $emit('foo', 1, 2, 3) the listener function will receive three arguments.

- __Declaring Emitted Events__: A component can explicitly declare the events it will emit using the defineEmits() macro:
```
<script setup>
defineEmits(['inFocus', 'submit'])
</script>
```

- The $emit method that we used in the `<template>` isn't accessible within the `<script setup>` section of a component, but defineEmits() returns an equivalent function that we can use instead:
```
<script setup>
const emit = defineEmits(['inFocus', 'submit'])

function buttonClick() {
  emit('submit')
}
</script>
```

- The defineEmits() macro cannot be used inside a function, it must be placed directly within `<script setup>`, as in the example above.

- The emits option and defineEmits() macro also support an object syntax. If using TypeScript you can type arguments, which allows us to perform runtime validation of the payload of the emitted events:
```
<script setup lang="ts">
const emit = defineEmits({
  submit(payload: { email: string, password: string }) {
    // return `true` or `false` to indicate
    // validation pass / fail
  }
})
</script>
```

- Although optional, it is recommended to define all emitted events in order to better document how a component should work. It also allows Vue to exclude known listeners from fallthrough attributes, avoiding edge cases caused by DOM events manually dispatched by 3rd party code.

- If a native event (e.g., click) is defined in the emits option, the listener will now only listen to component-emitted click events and no longer respond to native click events (like below example).

- __Events Validation__: Similar to prop type validation, an emitted event can be validated if it is defined with the object syntax instead of the array syntax. To add validation, the event is assigned a function that receives the arguments passed to the emit call and returns a boolean to indicate whether the event is valid or not.
```
<script setup>
const emit = defineEmits({
  // No validation
  click: null,

  // Validate submit event
  submit: ({ email, password }) => {
    if (email && password) {
      return true
    } else {
      console.warn('Invalid submit event payload!')
      return false
    }
  }
})

function submitForm(email, password) {
  emit('submit', { email, password })
}
</script>
```