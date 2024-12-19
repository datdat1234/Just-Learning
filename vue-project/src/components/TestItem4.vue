<script setup></script>

<template>
  <h1 v-if="awesome">Vue is awesome!</h1>
</template>

<!-- 
- A v-else element must immediately follow a v-if or a v-else-if element - otherwise it will not be recognized.

- Similar to v-else, a v-else-if element must immediately follow a v-if or a v-else-if element.

- v-if is "real" conditional rendering because it ensures that event listeners and child components inside the

- Because v-if is a directive, it has to be attached to a single element. But what if we want to toggle more than 
one element? In this case we can use v-if on a <template> element, which serves as an invisible wrapper. 
The final rendered result will not include the <template> element.

- Another option for conditionally displaying an element is the v-show directive. The usage is largely the same:
<h1 v-show="ok">Hello!</h1>
The difference is that an element with v-show will always be rendered and remain in the DOM; v-show only toggles 
the display CSS property of the element. v-show doesn't support the <template> element, nor does it work with v-else.

- v-if is "real" conditional rendering because it ensures that event listeners and child components inside the conditional 
block are properly destroyed and re-created during toggles.

- v-if is also lazy: if the condition is false on initial render, it will not do anything - the conditional block won't be 
rendered until the condition becomes true for the first time.

- In comparison, v-show is much simpler - the element is always rendered regardless of initial condition, with CSS-based toggling.

- Generally speaking, v-if has higher toggle costs while v-show has higher initial render costs. So prefer v-show if you need to 
toggle something very often, and prefer v-if if the condition is unlikely to change at runtime.

- When v-if and v-for are both used on the same element, v-if will be evaluated first. So, It's not recommended to use v-if and 
v-for on the same element due to implicit precedence
-->
