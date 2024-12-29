<script setup>
import { ref } from 'vue'

const isActive = ref(true)
const hasError = ref(false)
</script>

<template>
  <div
    class="static"
    :class="{ active: isActive, 'text-danger': hasError }"
  ></div>
</template>