<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
      <router-link to="/" class="navbar-brand">
        Expense Tracker App
      </router-link>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/about">About</router-link>
          </li>
          <li class="nav-item" v-if="!User.isLoggedIn">
            <router-link class="nav-link" to="/login">Login</router-link>
          </li>
          <li class="nav-item" v-if="!User.isLoggedIn">
            <router-link class="nav-link" to="/signup">Sign Up</router-link>
          </li>
          <li class="nav-item dropdown" v-if="User.isLoggedIn">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarUserDropdown"
              role="button"
              data-toggle="dropdown">
              {{ User.username }}
            </a>
            <div class="dropdown-menu">
              <router-link
                to="/dashboard"
                class="dropdown-item">
                Dashboard
              </router-link>
              <router-link
                to="/user/settings"
                class="dropdown-item">
                Settings
              </router-link>
              <div class="dropdown-divider"></div>
              <a
                class="dropdown-item"
                href="#"
                @click.prevent="logoutUser">
                Logout
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapState, mapActions } from 'vuex';

import Auth from '../../API/Auth';

export default {
  name: 'NavBar',
  computed: mapState(['User']),
  methods: {
    ...mapActions({
      logout: 'User/logoutUser',
    }),
    logoutUser() {
      Auth.logout()
        .then(() => {
          this.logout();
          this.$router.push({
            name: 'login',
            params: {
              message: 'You are logged out successfully',
            },
          });
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.nav-link.router-link-exact-active {
  color: #ffffff;
  font-weight: bold;
}
</style>
