<template>
  <div class="container mt-3">
    <h1 class="text-center mb-5 mt-5">Login</h1>
    <form class="col-md-6 col-12 ml-auto mr-auto">
      <!-- Success Alert -->
      <success-alert
        :message="successMessage"
        v-if="successMessage"/>
      <!-- Error Alert -->
      <error-alert
        :error="serverError"
        v-if="serverError"/>
      <!-- username input -->
      <username-input />
      <!-- password input -->
      <password-input />

      <!-- Submit Button -->
      <button
        @click.prevent="submitForm"
        type="submit"
        class="btn btn-primary btn-block btn-lg mt-4"
        :disabled="!UI.user.username || !UI.user.password || isLoading">
        {{ isLoading ? 'Logging...' : 'Login'}}
      </button>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

import SuccessAlert from '../components/UI/SuccessAlert.vue';
import ErrorAlert from '../components/UI/ErrorAlert.vue';
import UsernameInput from '../components/UI/forms/UsernameInput.vue';
import PasswordInput from '../components/UI/forms/PasswordInput.vue';
import Auth from '../API/Auth';

export default {
  name: 'Login',
  components: {
    ErrorAlert,
    SuccessAlert,
    UsernameInput,
    PasswordInput,
  },
  data: () => ({
    isLoading: false,
    serverError: null,
    successMessage: '',
  }),
  computed: mapState(['UI']),
  mounted() {
    if (this.$route.params.message) {
      this.successMessage = this.$route.params.message;
    }
    if (this.$route.params.error) {
      this.serverError = this.$route.params.error;
    }
  },
  methods: {
    ...mapActions({
      clearUIUser: 'UI/clearUIUser',
      clearUIUserPassword: 'UI/clearUIUserPassword',
      loginUser: 'User/loginUser',
      setUserSettings: 'User/setUserSettings',
    }),
    submitForm() {
      this.successMessage = '';
      // getting user from store
      const user = {
        username: this.UI.user.username,
        password: this.UI.user.password,
      };
      this.isLoading = true;
      // Logging In the user
      Auth.login(user)
        .then((res) => {
          this.serverError = null;
          // Clearing the user in store
          this.clearUIUser();
          // Logging in the user
          this.loginUser({
            username: res.username,
            token: res.token,
          });
          // Setting user's settings
          this.setUserSettings(res.settings);
          // Pushing the router to login
          this.$router.push({
            name: 'dashboard',
          });
        })
        .catch((err) => {
          // clearing user password in store
          this.clearUIUserPassword();
          this.serverError = err;
        });
      this.isLoading = false;
    },
  },
};
</script>

<style>

</style>
