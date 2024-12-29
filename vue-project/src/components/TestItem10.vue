<script setup>
import { ref } from 'vue'

var a = 1
const b = ref(2)
var c = 5
// Compiler macros, such as defineExpose, don't need to be imported
defineExpose({
  a,
  b
})
</script>

<template>
  <h1>hello</h1>
</template>