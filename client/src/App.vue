<template>
  <div id="app">
    <nav-bar />
    <router-view/>
  </div>
</template>

<script>
import 'bootstrap/dist/js/bootstrap.bundle';

import NavBar from './components/UI/NavBar.vue';
import Auth from './API/Auth';

export default {
  name: 'app',
  components: { NavBar },
  async mounted() {
    // If token exists in the localStorage,
    // then validating it and updating user settings in store
    if (localStorage.token) {
      this.$store.commit('User/setUserLoggedIn', localStorage.token);
      // Validating the token
      await Auth.validateToken(localStorage.token)
        .then((res) => {
          this.$store.commit('User/setUserLoggedIn');
          this.$store.commit('User/setUsername', res.username);
          this.$store.dispatch('User/setUserSettings', res.settings);
        })
        .catch(() => localStorage.removeItem('token'));
    }
  },
};
</script>
