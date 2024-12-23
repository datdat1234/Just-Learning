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
- Inside the v-for scope, template expressions have access to all parent scope properties.

- You can also use v-for to iterate through the properties of an object. The iteration order 
will be based on the result of calling Object.values() on the object
EX: 
const myObject = reactive({
  title: 'How to do lists in Vue',
  author: 'Jane Doe',
  publishedAt: '2016-04-10'
})

<ul>
  <li v-for="value in myObject">
    {{ value }}
  </li>
</ul>

- v-for can also take an integer. In this case it will repeat the template that many times, based on a range of 1...n
EX: <span v-for="n in 10">{{ n }}</span>

- Similar to template v-if, you can also use a <template> tag with v-for to render a block of multiple elements
EX: 
<ul>
  <template v-for="item in items">
    <li>{{ item.msg }}</li>
    <li class="divider" role="presentation"></li>
  </template>
</ul>

- It's not recommended to use v-if and v-for on the same element due to implicit precedence. v-if has a higher priority than v-for.

- When Vue is updating a list of elements rendered with v-for, by default it uses an "in-place patch" (Cập nhật dữ liệu tại chỗ) strategy. 
If the order of the data items has changed, instead of moving the DOM elements to match the order of the items, Vue will patch 
each element in-place and make sure it reflects what should be rendered at that particular index. This default mode is efficient, 
but only suitable when your list render output does not rely on child component state or temporary DOM state (e.g. form input values).
(This method have more performance)

- To give Vue a hint so that it can track each node's identity, and thus reuse and reorder existing elements, you need to provide 
a unique key attribute for each item:
EX:
<div v-for="item in items" :key="item.id">
</div>

- It is recommended to provide a key attribute with v-for whenever possible, unless the iterated DOM content is simple (i.e. contains no 
components or stateful DOM elements), or you are intentionally relying on the default behavior for performance gains.

- You can directly use v-for on a component, like any normal element. However, this won't automatically pass any data to the component, 
because components have isolated scopes of their own. In order to pass the iterated data into the component, we should also use props:
EX:
<MyComponent
  v-for="(item, index) in items"
  :item="item"
  :index="index"
  :key="item.id"
/>

- The reason for not automatically injecting item into the component is because that makes the component tightly coupled to how v-for 
works. Being explicit about where its data comes from makes the component reusable in other situations.

- Mutation methods, as the name suggests, mutate the original array they are called on. In comparison, there are also non-mutating 
methods, e.g. filter(), concat() and slice(), which do not mutate the original array but always return a new array. When working with 
non-mutating methods, we should replace the old array with the new one
EX: items.value = items.value.filter((item) => item.message.match(/Foo/))

- You might think this will cause Vue to throw away the existing DOM and re-render the entire list - luckily, that is not the case. 
Vue implements some smart heuristics to maximize DOM element reuse, so replacing an array with another array containing overlapping 
objects is a very efficient operation.

- Sometimes we want to display a filtered or sorted version of an array without actually mutating or resetting the original data. 
In this case, you can create a computed property that returns the filtered or sorted array.
EX:
const numbers = ref([1, 2, 3, 4, 5])

const evenNumbers = computed(() => {
  return numbers.value.filter((n) => n % 2 === 0)
})
  
- Be careful with reverse() and sort() in a computed property! These two methods will mutate the original array, which should be 
avoided in computed getters. Create a copy of the original array before calling these methods:
EX:
- return numbers.reverse()
+ return [...numbers].reverse()
-->