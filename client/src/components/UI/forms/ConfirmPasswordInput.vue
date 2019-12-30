<template>
  <div class="form-group">
    <label for="confirmPassword">Confirm Password</label>
    <input
      v-model="UI.user.confirmPassword"
      @input="validateConfirmPassword"
      type="password"
      class="form-control"
      id="confirmPassword"
      placeholder="Confirm your Password"
      :class="{
        'is-invalid': error,
        'is-valid': (UI.user.confirmPassword && !error)
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
    validateConfirmPassword(e) {
      // Getting confirmPassword from the input
      const confirmPassword = e.target.value.trim();

      // Validating the confirmPassword
      if (confirmPassword === '') {
        this.error = 'Confirm Password is required';
      } else if (confirmPassword !== this.UI.user.password) {
        this.error = 'Confirm Password and Password does not match.';
      } else {
        // Saving confirmPassword in store if it is valid
        this.$store.commit('UI/setUIConfirmPassword', confirmPassword);
        this.error = '';
      }
    },
  },
};
</script>
