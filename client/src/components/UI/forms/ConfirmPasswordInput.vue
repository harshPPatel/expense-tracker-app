<template>
  <div class="form-group">
    <label for="confirmPassword">Confirm Password</label>
    <input
      @input="validateConfirmPassword"
      type="password"
      class="form-control"
      id="confirmPassword"
      placeholder="Confirm your Password"
      :value="UI.user.confirmPassword"
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
      if (!confirmPassword) {
        this.error = 'Confirm Password is required';
        return;
      }
      // Saving confirmPassword in store if it is valid
      this.$store.commit('UI/setUIConfirmPassword', confirmPassword);
      if (confirmPassword !== this.UI.user.password) {
        this.error = 'Confirm Password and Password does not match.';
      } else {
        this.error = '';
      }
    },
  },
};
</script>
