<template>
  <div id="app">
    <PygHeader></PygHeader>
    <router-view></router-view>
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