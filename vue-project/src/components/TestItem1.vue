<script setup>
import { ref } from 'vue'

const count = ref(0)

console.log(count) // { value: 0 }
console.log(count.value) // 0

count.value++
console.log(count.value) // 1
</script>

<template>
  <p>Using text interpolation: {{ rawHtml }}</p>
  <p>Using v-html directive: <span v-html="rawHtml"></span></p>
  <h1>Wanna test?</h1>
</template>

<style scoped>
</style>

<!-- 
- Notice that we did not need to append .value when using the ref in the template

- Script does not need to appqend .value

- Top-level imports, variables and functions declared in <script setup> are automatically 
usable in the template of the same component. Think of the template as a JavaScript 
function declared in the same scope - it naturally has access to everything declared alongside it.

- When you use a ref in a template, and change the ref's value later, Vue automatically detects 
the change and updates the DOM accordingly. This is made possible with a dependency-tracking 
based reactivity system. When a component is rendered for the first time, Vue tracks every 
ref that was used during the render. Later on, when a ref is mutated, it will trigger a re-render 
for components that are tracking it.

- The .value property gives Vue the opportunity to detect when a ref has been accessed or mutated. 
Under the hood, Vue performs the tracking in its getter, and performs triggering in its setter
=> Every time component access a ref, Vue will track it and when it is mutated, Vue will trigger
=> Every time a ref value is updated, Vue will re-render the component that is tracking it

- A ref will make its value deeply reactive. This means you can expect changes to be detected 
even when you mutate nested objects or arrays.

- When you mutate reactive state in Vue, the resulting DOM updates are not applied synchronously. 
Instead, Vue buffers them until the "next tick" to ensure that each component updates only once 
no matter how many state changes you have made.

- reactive() converts the object deeply: nested objects are also wrapped with reactive() when accessed. 
It is also called by ref() internally when the ref value is an object. Similar to shallow refs, 
there is also the shallowReactive() API for opting-out of deep reactivity.

- To ensure consistent access to the proxy, calling reactive() on the same object always returns the 
same proxy, and calling reactive() on an existing proxy also returns that same proxy:
// calling reactive() on the same object returns the same proxy
console.log(reactive(raw) === proxy) // true

// calling reactive() on a proxy returns itself
console.log(reactive(proxy) === proxy) // true

- The reactive() API has a few limitations, so we recommend using ref() as the primary API for declaring reactive state.

- Ref unwrapping: Only happens when nested inside a deep reactive object. It does not apply when it is accessed 
as a property of a shallow reactive object

- Ref unwrapping: Details and Ceveats
-->