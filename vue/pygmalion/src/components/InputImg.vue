<template>
<div>
  <b-img v-bind:src="require('../assets/'+propsdata)" fluid-grow alt="Fluid-grow image"></b-img>
</div>
</template>

<script>
export default {
    props: ['propsdata']
}
</script>

<style>

</style>