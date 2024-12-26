<script setup>
import { useTemplateRef, onMounted } from 'vue'
import TestItem10 from './components/TestItem10.vue'

const childRef = useTemplateRef('child')

onMounted(() => {
  childRef.value.c = 4;
  console.log(childRef.value.c)
})
</script>

<template>
  <div class="container">
    <TestItem10 ref="child" />
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}
</style>
