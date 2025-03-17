## JavaScript Hooks
​
- You can hook into the transition process with JavaScript by listening to events on the `<Transition>` component:

```html
<Transition
  @before-enter="onBeforeEnter"
  @enter="onEnter"
  @after-enter="onAfterEnter"
  @enter-cancelled="onEnterCancelled"
  @before-leave="onBeforeLeave"
  @leave="onLeave"
  @after-leave="onAfterLeave"
  @leave-cancelled="onLeaveCancelled"
>
  <!-- ... -->
</Transition>
```


```js
// called before the element is inserted into the DOM.
// use this to set the "enter-from" state of the element
function onBeforeEnter(el) {}

// called one frame after the element is inserted.
// use this to start the entering animation.
function onEnter(el, done) {
  // call the done callback to indicate transition end
  // optional if used in combination with CSS
  done()
}

// called when the enter transition has finished.
function onAfterEnter(el) {}

// called when the enter transition is cancelled before completion.
function onEnterCancelled(el) {}

// called before the leave hook.
// Most of the time, you should just use the leave hook
function onBeforeLeave(el) {}

// called when the leave transition starts.
// use this to start the leaving animation.
function onLeave(el, done) {
  // call the done callback to indicate transition end
  // optional if used in combination with CSS
  done()
}

// called when the leave transition has finished and the
// element has been removed from the DOM.
function onAfterLeave(el) {}

// only available with v-show transitions
function onLeaveCancelled(el) {}
```

- These hooks can be used in combination with CSS transitions / animations or on their own.

- When using JavaScript-only transitions, it is usually a good idea to add the :css="false" prop. This explicitly tells Vue to skip auto CSS transition detection. Aside from being slightly more performant, this also prevents CSS rules from accidentally interfering with the transition:

```template
<Transition
  ...
  :css="false"
>
  ...
</Transition>
```

- With :css="false", we are also fully responsible for controlling when the transition ends. In this case, the done callbacks are required for the @enter and @leave hooks. Otherwise, the hooks will be called synchronously and the transition will finish immediately.

## Reusable Transitions
​
- Transitions can be reused through Vue's component system. To create a reusable transition, we can create a component that wraps the `<Transition>` component and passes down the slot content:

```vue
<!-- MyTransition.vue -->
<script>
// JavaScript hooks logic...
</script>

<template>
  <!-- wrap the built-in Transition component -->
  <Transition
    name="my-transition"
    @enter="onEnter"
    @leave="onLeave">
    <slot></slot> <!-- pass down slot content -->
  </Transition>
</template>

<style>
/*
  Necessary CSS...
  Note: avoid using <style scoped> here since it
  does not apply to slot content.
*/
</style>
```

- Now MyTransition can be imported and used just like the built-in version:

```template
<MyTransition>
  <div v-if="show">Hello</div>
</MyTransition>
```

## Transition on Appear
​
- If you also want to apply a transition on the initial render of a node, you can add the appear prop:

```template
<Transition appear>
  ...
</Transition>
```

## Transition Between Elements
​
- In addition to toggling an element with v-if / v-show, we can also transition between two elements using v-if / v-else / v-else-if, as long as we make sure that there is only one element being shown at any given moment:

```template
<Transition>
  <button v-if="docState === 'saved'">Edit</button>
  <button v-else-if="docState === 'edited'">Save</button>
  <button v-else-if="docState === 'editing'">Cancel</button>
</Transition>
```

## Transition Modes
​
- In the previous example, the entering and leaving elements are animated at the same time, and we had to make them position: absolute to avoid the layout issue when both elements are present in the DOM.

- However, in some cases this isn't an option, or simply isn't the desired behavior. We may want the leaving element to be animated out first, and for the entering element to only be inserted after the leaving animation has finished. Orchestrating such animations manually would be very complicated - luckily, we can enable this behavior by passing `<Transition>` a mode prop:

```template
<Transition mode="out-in">
  ...
</Transition>
```

## Transition Between Components
​
- `<Transition>` can also be used around dynamic components:

```template
<Transition name="fade" mode="out-in">
  <component :is="activeComponent"></component>
</Transition>
```

## Dynamic Transitions
​
- `<Transition>` props like name can also be dynamic! It allows us to dynamically apply different transitions based on state change:

```template
<Transition :name="transitionName">
  <!-- ... -->
</Transition>
```

- This can be useful when you've defined CSS transitions / animations using Vue's transition class conventions and want to switch between them.

- You can also apply different behavior in JavaScript transition hooks based on the current state of your component. Finally, the ultimate way of creating dynamic transitions is through reusable transition components that accept props to change the nature of the transition(s) to be used. It may sound cheesy, but the only limit really is your imagination.

## Transitions with the Key Attribute
​
- Sometimes you need to force the re-render of a DOM element in order for a transition to occur.

- Take this counter component for example:

```vue
<script setup>
import { ref } from 'vue';
const count = ref(0);

setInterval(() => count.value++, 1000);
</script>

<template>
  <Transition>
    <span :key="count">{{ count }}</span>
  </Transition>
</template>
```

- If we had excluded the key attribute, only the text node would be updated and thus no transition would occur. However, with the key attribute in place, Vue knows to create a new span element whenever count changes and thus the Transition component has 2 different elements to transition between.