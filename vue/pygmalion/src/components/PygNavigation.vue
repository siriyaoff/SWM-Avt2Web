<template>
<div>
  <b-navbar toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="/main" id="brand">Pygmalion</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/service">Service</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
export default {

}
</script>

<style scoped>
#brand {
  margin-left: 20px;
}
</style>