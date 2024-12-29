<script setup>
import { ref } from 'vue'
const selected = ref('A')
const msg = ref('A')

const options = ref([
  { text: 'One', value: 'A' },
  { text: 'Two', value: 'B' },
  { text: 'Three', value: 'C' }
])
</script>
<template>
  <select v-model="selected">
    <option v-for="option in options" :value="option.value">
      {{ option.text }}
    </option>
  </select>

  <div>Selected: {{ selected }}</div>

  <input v-model.lazy="msg" />
  <h1>{{ msg }}</h1>
</template>
<style></style>