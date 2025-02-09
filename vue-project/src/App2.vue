<script setup>
import { useTemplateRef, onMounted } from 'vue'
import TestItem15 from './components/TestItem15.vue'
</script>

<template>
  <div class="container">
    <component :is="TestItem15"></component>
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
