<script setup>
import TestItem3 from './TestItem3.vue';
import { ref, onMounted, defineAsyncComponent } from 'vue'

const AsyncComp = defineAsyncComponent({
  // the loader function
  loader: () => import('./TempComp.vue'),

  // A component to use while the async component is loading
  loadingComponent: TestItem3,
  // Delay before showing the loading component. Default: 200ms.
  delay: 2000,

  // A component to use if the load fails
  errorComponent: TestItem3,
  // The error component will be displayed if a timeout is
  // provided and exceeded. Default: Infinity.
  timeout: 3000
})

const isActive = ref(false)

onMounted(() => {
  setTimeout(() => {
    isActive.value = true
  }, 2000)
})
</script>

<template>
  <AsyncComp v-if="isActive"/>
</template>