# https://vuejs.org/tutorial/

## Welcome to the Vue tutorial!

- The core feature of Vue is declarative rendering: using a template syntax that extends HTML, we can describe how the HTML should look based on JavaScript state. When the state changes, the HTML updates automatically.

- State that can trigger updates when changed is considered reactive. We can declare reactive state using Vue's reactive() API. Objects created from reactive() are JavaScript Proxies that work just like normal objects.
```js
import { reactive } from 'vue'

const counter = reactive({
  count: 0
})

console.log(counter.count) // 0
counter.count++
```

- reactive() only works on objects (including arrays and built-in types like Map and Set). ref(), on the other hand, can take any value type and create an object that exposes the inner value under a .value property.

- Reactive state declared in the component's `<script setup>` block can be used directly in the template. This is how we can render dynamic text based on the value of the counter object and message ref, using mustaches syntax.

- Notice how we did not need to use .value when accessing the message ref in templates: it is automatically unwrapped for more succinct usage.

- A directive is a special attribute that starts with the v- prefix. They are part of Vue's template syntax. Similar to text interpolations, directive values are JavaScript expressions that have access to the component's state. Because v-bind is used so frequently, it has a dedicated shorthand syntax:
```
<div :id="dynamicId"></div>
```

- __Event Listeners__: We can listen to DOM events using the v-on directive:
```
<button v-on:click="increment">{{ count }}</button>
```

- Event handlers can also use inline expressions, and can simplify common tasks with modifiers.

- __Form Bindings__: Using v-bind and v-on together, we can create two-way bindings on form input elements:
```template
<input :value="text" @input="onInput">
```
```js
function onInput(e) {
  // a v-on handler receives the native DOM event
  // as the argument.
  text.value = e.target.value
}
```

- To simplify two-way bindings, Vue provides a directive, v-model, which is essentially syntactic sugar for the above:
```
<input v-model="text">
```

- v-model automatically syncs the `<input>`'s value with the bound state, so we no longer need to use an event handler for that.

- __Conditional Rendering__: We can use the v-if directive to conditionally render an element:
```
<h1 v-if="awesome">Vue is awesome!</h1>
```

- This `<h1>` will be rendered only if the value of awesome is truthy. If awesome changes to a falsy (All values are truthy except false, 0, -0, 0n, "", null, undefined, NaN, and document.all) value, it will be removed from the DOM.

- We can also use v-else and v-else-if to denote other branches of the condition:
```template
<h1 v-if="awesome">Vue is awesome!</h1>
<h1 v-else>Oh no 😢</h1>
```

- __List Rendering__: We can use the v-for directive to render a list of elements based on a source array:
```template
<ul>
  <li v-for="todo in todos" :key="todo.id">
    {{ todo.text }}
  </li>
</ul>
```

- Notice how we are also giving each todo object a unique id, and binding it as the special key attribute for each `<li>`. The key allows Vue to accurately move each `<li>` to match the position of its corresponding object in the array.

- There are two ways to update the list:
   - Call mutating methods on the source array:
   ```js
   todos.value.push(newTodo)
   ```

   - Replace the array with a new one:
   ```js
   todos.value = todos.value.filter(/* ... */)
   ```

- __Computed Property__: Introducing computed(). We can create a computed ref that computes its .value based on other reactive data sources:
```js
import { ref, computed } from 'vue'

const hideCompleted = ref(false)
const todos = ref([
  /* ... */
])

const filteredTodos = computed(() => {
  // return filtered todos based on
  // `todos.value` & `hideCompleted.value`
})
```

- A computed property tracks other reactive state used in its computation as dependencies. It caches the result and automatically updates it when its dependencies change.

- __Lifecycle and Template Refs__: So far, Vue has been handling all the DOM updates for us, thanks to reactivity and declarative rendering. However, inevitably there will be cases where we need to manually work with the DOM.

- We can request a template ref - i.e. a reference to an element in the template - using the special ref attribute:
```template
<p ref="pElementRef">hello</p>
```

- To access the ref, we need to declare a ref with matching name:
```js
const pElementRef = ref(null)
```

- Notice the ref is initialized with null value. This is because the element doesn't exist yet when `<script setup>` is executed. The template ref is only accessible after the component is __mounted__.

- To run code after mount, we can use the onMounted() function:
```js
import { onMounted } from 'vue'

onMounted(() => {
  // component is now mounted.
})
```

- This is called a lifecycle hook - it allows us to register a callback to be called at certain times of the component's lifecycle. There are other hooks such as onUpdated and onUnmounted. Check out the Lifecycle Diagram for more details.

- __Watchers__: Sometimes we may need to perform "side effects" reactively - for example, logging a number to the console when it changes. We can achieve this with watchers:
```js
import { ref, watch } from 'vue'

const count = ref(0)

watch(count, (newCount) => {
  // yes, console.log() is a side effect
  console.log(`new count is: ${newCount}`)
})
```

- watch() can directly watch a ref, and the callback gets fired whenever count's value changes. watch() can also watch other types of data sources - more details are covered in Guide - Watchers.

- __Components__: So far, we've only been working with a single component. Real Vue applications are typically created with nested components.

- A parent component can render another component in its template as a child component. To use a child component, we need to first import it:
```js
import ChildComp from './ChildComp.vue'
```

- Then, we can use the component in the template as:
```template
<ChildComp />
```

- __Props__: A child component can accept input from the parent via props. First, it needs to declare the props it accepts:
```vue
<!-- ChildComp.vue -->
<script setup>
const props = defineProps({
  msg: String
})
</script>
```

- Note defineProps() is a compile-time macro and doesn't need to be imported. Once declared, the msg prop can be used in the child component's template. It can also be accessed in JavaScript via the returned object of defineProps().

- The parent can pass the prop to the child just like attributes. To pass a dynamic value, we can also use the v-bind syntax:
```template
<ChildComp :msg="greeting" />
```

- __Emits__: In addition to receiving props, a child component can also emit events to the parent:
```vue
<script setup>
// declare emitted events
const emit = defineEmits(['response'])

// emit with argument
emit('response', 'hello from child')
</script>
```

- The first argument to emit() is the event name. Any additional arguments are passed on to the event listener.

- The parent can listen to child-emitted events using v-on - here the handler receives the extra argument from the child emit call and assigns it to local state:
```template
<ChildComp @response="(msg) => childMsg = msg" />
```

- __Slots__: In addition to passing data via props, the parent component can also pass down template fragments to the child via slots:
```template
<ChildComp>
  This is some slot content!
</ChildComp>
```

- In the child component, it can render the slot content from the parent using the `<slot>` element as outlet:
```template
<!-- in child template -->
<slot/>
```

- Content inside the `<slot>` outlet will be treated as "fallback" content: it will be displayed if the parent did not pass down any slot content:
```template
<slot>Fallback content</slot>
```