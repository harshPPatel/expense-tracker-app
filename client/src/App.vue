<template>
  <div id="app">
    <nav-bar />
    <router-view/>
  </div>
</template>

<script>
import 'bootstrap/dist/js/bootstrap.bundle';
import NavBar from './components/UI/NavBar.vue';

export default {
  name: 'app',
  components: { NavBar },
};
</script>
