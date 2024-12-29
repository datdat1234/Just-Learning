<script setup>
import { ref } from 'vue'
const items = ref([{ label: 'Foo' }, { label: 'Bar' }])
</script>
<template>
  <ul>
    <li v-for="item in items">
      {{ item.label }} <input type="text" />
    </li>
  </ul>
</template>
<style>
</style>