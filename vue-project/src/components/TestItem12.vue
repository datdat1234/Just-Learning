<script setup>
import { onMounted, onBeforeMount, onBeforeUpdate, onUpdated, ref } from 'vue'
const inputText = ref('')
const tmp = ref([])

onMounted(() => {
  console.log('onMounted')
})

onBeforeMount(() => {
  console.log('onBeforeMount')
})

onBeforeUpdate(() => {
  console.log('onBeforeUpdate')
})

onUpdated(() => {
  console.log('onUpdated')
})

setTimeout(() => {
  for (let i = 0; i < 4; ++i) {
    tmp.value[i] = i
  }
  console.log('Hello')
}, 1000)
</script>

<template>
  <input v-model="inputText" />
  <div>
    <ul>
      <li v-for="item in tmp" :key="item">{{ item }}</li>
    </ul>
  </div>
</template>

<!-- 
- Each Vue component instance goes through a series of initialization steps when it's created - for example, it needs to set up data 
observation, compile the template, mount the instance to the DOM, and update the DOM when data changes. Along the way, it also runs 
functions called lifecycle hooks, giving users the opportunity to add their own code at specific stages.
-->
