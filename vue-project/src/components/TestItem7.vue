<script setup>
import { ref } from 'vue'
const items = ref([{ label: 'Foo' }, { label: 'Bar' }])
</script>
<template>
  <ul>
    <li v-for="item in items">
      {{ item.label }} <input type="text" />
    </li>
  </ul>
</template>
<style>
</style>

<!-- 
- Inline Handlers: Inline handlers are typically used in simple cases

- A method handler automatically receives the native DOM Event object that triggers it - in the example above, 
we are able to access the element dispatching the event via event.target
EX:
const name = ref('Vue.js')

function greet(event) {
  alert(`Hello ${name.value}!`)
  // `event` is the native DOM event
  if (event) {
    alert(event.target.tagName)
  }
}

- Instead of binding directly to a method name, we can also call methods in an inline handler. This allows us to 
pass the method custom arguments instead of the native event like above:
EX:
function say(message) {
  alert(message)
}

<button @click="say('hello')">Say hello</button>
<button @click="say('bye')">Say bye</button>

- Accessing Event Argument in Inline Handlers: Sometimes we also need to access the original DOM event in an inline 
handler. You can pass it into a method using the special $event variable, or use an inline arrow function:
EX:
<button @click="warn('Form cannot be submitted yet.', $event)">
  Submit
</button>

<button @click="(event) => warn('Form cannot be submitted yet.', event)">
  Submit
</button>

function warn(message, event) {
  // now we have access to the native event
  if (event) {
    event.preventDefault()
  }
  alert(message)
}

- Event Modifiers
​EX:
<a @click.stop="doThis"></a>
<form @submit.prevent="onSubmit"></form>
<a @click.stop.prevent="doThat"></a>
<form @submit.prevent></form>
<div @click.self="doThat">...</div>

- Key Modifiers
EX:
​<input @keyup.enter="submit" />
<input @keyup.page-down="onPageDown" />
<input @keyup.alt.enter="clear" />
<div @click.ctrl="doSomething">Do something</div>
<button @click.ctrl.exact="onCtrlClick">A</button>

- Mouse Button Modifiers
EX:
.left
.right
.middle
-->