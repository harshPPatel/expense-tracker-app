<template>
  <div class="container mt-3">
    <h1 class="text-center mb-5 mt-5">Sign Up</h1>
    <form class="col-md-6 col-12 ml-auto mr-auto">
      <!-- Error Alert -->
      <error-alert
        :error="serverError"
        v-if="serverError"/>
      <!-- username input -->
      <username-input />
      <!-- password input -->
      <password-input />
      <!-- confirm password input -->
      <confirm-password-input />

      <!-- Submit Button -->
      <button
        @click.prevent="submitForm"
        type="submit"
        class="btn btn-primary btn-block btn-lg mt-4"
        :disabled="!UI.user.username || !UI.user.password || !UI.user.confirmPassword || isLoading">
        {{ isLoading ? 'Signing Up...' : 'Sign Up'}}
      </button>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

import ErrorAlert from '../components/UI/ErrorAlert.vue';
import UsernameInput from '../components/UI/forms/UsernameInput.vue';
import PasswordInput from '../components/UI/forms/PasswordInput.vue';
import ConfirmPasswordInput from '../components/UI/forms/ConfirmPasswordInput.vue';
import Auth from '../API/Auth';

export default {
  name: 'SignUp',
  components: {
    ErrorAlert,
    UsernameInput,
    PasswordInput,
    ConfirmPasswordInput,
  },
  data: () => ({
    isLoading: false,
    serverError: null,
  }),
  computed: mapState(['UI']),
  methods: {
    ...mapActions({
      clearUIUser: 'UI/clearUIUser',
      clearUIUserPassword: 'UI/clearUIUserPassword',
    }),
    submitForm() {
      // getting user from store
      const user = { ...this.UI.user };
      this.isLoading = true;
      // Signing Up the user
      Auth.signup(user)
        .then((res) => {
          this.serverError = null;
          // Clearing the user in store
          this.clearUIUser();
          // Pushing the router to login
          this.$router.push({
            name: 'login',
            params: {
              signupMessage: res.message,
            },
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
