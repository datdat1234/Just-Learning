<script setup>
const props = defineProps(['title', 'likes'])
props.title = 'Bello world?'
</script>
<template>
  <h1>Bello world?</h1>
  <h1>{{ title }}</h1>
  <h1>{{ likes }}</h1>
</template>
