<template>
  <div id="app">
    <div id="nav">
      <nav-bar />
    </div>
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

<style lang="scss">

// #nav {
//   padding: 30px;

//   a {
//     font-weight: bold;
//     color: #2c3e50;

//     &.router-link-exact-active {
//       color: #42b983;
//     }
//   }
// }
</style>
