<script setup>
let rawHtml = '<span style="color: red">This should be red.</span>';
</script>

<template>
  <p>Using text interpolation: {{ rawHtml }}</p>
  <p>Using v-html directive: <span v-html="rawHtml"></span></p>
  <h1>Wanna test?</h1>
</template>

<style scoped>
</style>