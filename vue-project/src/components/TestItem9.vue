<script setup>
import { ref, watch } from 'vue'

const question = ref('')
const answer = ref('Questions usually contain a question mark. ;-)')
const loading = ref(false)
const source = ref(false)

watch(
  source,
  (newValue, oldValue) => {
    console.log('hehe')
    // executed immediately, then again when `source` changes
  },
  { immediate: true }
)
</script>

<template>
  <p>
    Ask a yes/no question:
    <input v-model="question" :disabled="loading" />
  </p>
  <p>{{ answer }}</p>
</template>