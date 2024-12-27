<script setup>
const props = defineProps(['title'])
console.log(props.title)
</script>

<template>
  <h4>
    Hello
  </h4>
</template>

<!-- 
- Defining a Component: When using a build step, we typically define each Vue component in a dedicated file using the .vue extension - 
known as a Single-File Component (SFC for short):

<script setup>
import { ref } from 'vue'

const count = ref(0)
</script>

<template>
  <button @click="count++">You clicked me {{ count }} times.</button>
</template>

- When not using a build step, a Vue component can be defined as a plain JavaScript object containing Vue-specific options.

import { ref } from 'vue'

export default {
  setup() {
    const count = ref(0)
    return { count }
  },
  template: `
    <button @click="count++">
      You clicked me {{ count }} times.
    </button>`
  // Can also target an in-DOM template:
  // template: '#my-template-element'
}

- Components can be reused as many times as you want, but  each time you use a component, a new instance of it is created.

- In SFCs, it's recommended to use PascalCase tag names for child components to differentiate from native HTML elements. Although 
native HTML tag names are case-insensitive, Vue SFC is a compiled format so we are able to use case-sensitive tag names in it. We 
are also able to use /> to close a tag.

- Passing Props: Props are custom attributes you can register on a component. To pass a data to our component, we must 
declare it in the list of props this component accepts, using the defineProps macro:

<script setup>
defineProps(['title'])
</script>

<template>
  <h4>{{ title }}</h4>
</template>

- defineProps is a compile-time macro that is only available inside <script setup> and does not need to be explicitly imported. Declared 
props are automatically exposed to the template. defineProps also returns an object that contains all the props passed to the component, 
so that we can access them in JavaScript if needed:

const props = defineProps(['title'])
console.log(props.title)

- A component can have as many props as you like and, by default, any value can be passed to any prop.

- Then the child component can emit an event on itself by calling the built-in $emit method, passing the name of the event:

<template>
  <div class="blog-post">
    <h4>{{ title }}</h4>
    <button @click="$emit('enlarge-text')">Enlarge text</button>
  </div>
</template>

- The parent can choose to listen to any event on the child component instance with v-on or @, just as we would with a native DOM event:

<BlogPost
  ...
  @enlarge-text="postFontSize += 0.1"
/>

- We can optionally declare emitted events using the defineEmits macro:

<script setup>
  defineProps(['title'])
  defineEmits(['enlarge-text'])
</script>

- Similar to defineProps, defineEmits is only usable in <script setup> and doesn't need to be imported. It returns an emit function that 
is equivalent to the $emit method. It can be used to emit events in the <script setup> section of a component, where $emit isn't directly 
accessible:

<script setup>
const emit = defineEmits(['enlarge-text'])

emit('enlarge-text')
</script>

- Content Distribution with Slots: Just like with HTML elements, it's often useful to be able to pass content to a component, like this:

<AlertBox>
  Something bad happened.
</AlertBox>

- This can be achieved using Vue's custom <slot> element:

<template>
  <div class="alert-box">
    <strong>This is an Error for Demo Purposes</strong>
    <slot />
  </div>
</template>

<style scoped>
.alert-box {
}
</style>

- As you'll see above, we use the <slot> as a placeholder where we want the content to go – and that's it. We're done!

- Sometimes, it's useful to dynamically switch between components, like in a tabbed interface. This is made possible by 
Vue's <component> element with the special is attribute:

<component :is="tabs[currentTab]"></component>

- In the example above, the value passed to :is can contain either:
+ The name string of a registered component, OR
+ The actual imported component object

- When switching between multiple components with <component :is="...">, a component will be unmounted when it is switched 
away from. We can force the inactive components to stay "alive" with the built-in <KeepAlive> component.

- in-DOM Template Parsing Caveats: It should be noted that the limitations discussed below only apply if you are writing 
your templates directly in the DOM due to browsers' native HTML parsing behavior.
+ Case Insensitivity: 

// camelCase in JavaScript
const BlogPost = {
  props: ['postTitle'],
  emits: ['updatePost'],
  template: `
    <h3>{{ postTitle }}</h3>
  `
}

<blog-post post-title="hello!" @update-post="onUpdatePost"></blog-post>

+ Self Closing Tags: <MyComponent />

+ Element Placement Restrictions: Some HTML elements, such as <ul>, <ol>, <table> and <select> have restrictions on what elements 
can appear inside them, and some elements such as <li>, <tr>, and <option> can only appear inside certain other elements.
-->
