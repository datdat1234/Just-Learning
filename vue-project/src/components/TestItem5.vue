<script setup>
import { shuffle } from 'lodash'
import { ref } from 'vue'

const items = ref([
  { id: 1, label: 'first name' },
  { id: 2, label: 'last name' },
  { id: 3, label: 'email' },
  { id: 4, label: 'website' }
])
const shuffleItems = () => {
  items.value = shuffle(items.value)
}
</script>
<template>
  <ul>
    <li v-for="item in items">
      {{ item.label }} <input type="text" />
    </li>
  </ul>
  <button @click="shuffleItems">Shuffle Items</button>
</template>
<style>
ul {
  margin: 0;
  padding: 0;
  padding-right: 20px;
  margin-bottom: 10px;
  list-style-type: none;
}
ul li {
  display: block;
  margin-bottom: 20px;
}
ul li input {
  display: block;
}
</style>