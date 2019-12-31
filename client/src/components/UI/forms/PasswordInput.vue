<template>
  <div class="form-group">
    <label for="password">Password</label>
    <input
      v-model="UI.user.password"
      @input="validatePassword"
      type="password"
      class="form-control"
      id="password"
      placeholder="Password"
      :class="{
        'is-invalid': error,
        'is-valid': (UI.user.password && !error)
      }">
    <div class="text-danger">
      {{ error }}
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'PasswordInput',
  data: () => ({
    error: '',
  }),
  computed: mapState(['UI']),
  methods: {
    validatePassword(e) {
      // getting password from input
      const password = e.target.value.trim();
      // regex to validate the password
      const passwordRegex = /(^[a-zA-Z0-9_]+$)/;

      // Validating the password
      if (!password) {
        this.error = 'Password is required';
      } else if (!password.match(passwordRegex)) {
        this.error = 'Password can contain only following characters: A to Z, a to z, 0 to 9, @ or _.';
      } else {
        // Saving password to store if it is valid
        this.$store.commit('UI/setUIPassword', password);
        this.error = '';
      }
    },
  },
};
</script>
