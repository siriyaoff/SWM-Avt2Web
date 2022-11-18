<template>
  <div id="app">
    <PygHeader></PygHeader>
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
import PygHeader from './components/PygHeader.vue'

export default {
  components: {
    PygHeader,
  }
}
</script>

<style>
</style>