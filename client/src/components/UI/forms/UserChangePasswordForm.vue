<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
      <error-alert
        v-if="serverError"
        :error="serverError" />
      <div class="form-group">
        <label for="currentPassword">Current Password</label>
        <input
          @input="validateCurrentPassword"
          type="password"
          class="form-control"
          id="currentPassword"
          placeholder="Current Password"
          :value="currentPassword"
          :class="{
            'is-invalid': currentPasswordError,
            'is-valid': (currentPassword && !currentPasswordError)
          }">
        <div class="text-danger">
          {{ currentPasswordError }}
        </div>
      </div>
      <div class="form-group">
        <label for="newPassword">New Password</label>
        <input
          @input="validateNewPassword"
          type="password"
          class="form-control"
          id="newPassword"
          placeholder="New Password"
          :value="newPassword"
          :class="{
            'is-invalid': newPasswordError,
            'is-valid': (newPassword && !newPasswordError)
          }">
        <div class="text-danger">
          {{ newPasswordError }}
        </div>
      </div>
      <div class="form-group">
        <label for="repeatPassword">Repeat Password</label>
        <input
          @input="validateRepeatPassword"
          type="password"
          class="form-control"
          id="repeatPassword"
          placeholder="Repeat Password"
          :value="repeatPassword"
          :class="{
            'is-invalid': repeatPasswordError,
            'is-valid': (repeatPassword && !repeatPasswordError)
          }">
        <div class="text-danger">
          {{ repeatPasswordError }}
        </div>
      </div>
      <div class="form-group text-right">
        <button type="submit" class="btn btn-primary" @click="changePassword">
          changePassword
        </button>
      </div>
    </div>
    <div class="col-lg-3"></div>
  </div>
</template>

<script>
import User from '../../../API/User';
import ErrorAlert from '../ErrorAlert.vue';

export default {
  name: 'UserChangePasswordForm',
  components: {
    ErrorAlert,
  },
  data: () => ({
    currentPassword: '',
    newPassword: '',
    repeatPassword: '',
    currentPasswordError: null,
    newPasswordError: null,
    repeatPasswordError: null,
    serverError: null,
  }),
  mounted() {
    this.expenseWarningLimit = this.$store.state.User.settings.expenseWarningLimit;
  },
  methods: {
    validateCurrentPassword(e) {
      // getting password from input
      const password = e.target.value.trim();
      // regex to validate the password
      const passwordRegex = /(^[a-zA-Z0-9_]+$)/;

      // Validating the password
      if (!password) {
        this.currentPasswordError = 'Current Password is required';
      } else if (!password.match(passwordRegex)) {
        this.currentPasswordError = 'Current Password can contain only following characters: A to Z, a to z, 0 to 9, @ or _.';
      } else {
        this.currentPassword = password;
        this.currentPasswordError = '';
      }
    },
    validateNewPassword(e) {
      // getting password from input
      const password = e.target.value.trim();
      // regex to validate the password
      const passwordRegex = /(^[a-zA-Z0-9_]+$)/;

      // Validating the password
      if (!password) {
        this.newPasswordError = 'New Password is required';
      } else if (password === this.currentPassword) {
        this.newPasswordError = 'New Password should be different than the current password';
      } else if (!password.match(passwordRegex)) {
        this.newPasswordError = 'New Password can contain only following characters: A to Z, a to z, 0 to 9, @ or _.';
      } else {
        this.newPassword = password;
        this.newPasswordError = '';
      }
    },
    validateRepeatPassword(e) {
      // getting password value from input
      const password = e.target.value;

      // Validating the password
      if (!password) {
        this.error = 'Repeat Password is required';
      } else if (password !== this.newPassword) {
        this.error = 'New Password and Repeat Password does not match.';
      } else {
        this.repeatPassword = password;
        this.error = '';
      }
    },
    changePassword() {
      this.serverError = null;
      const data = {
        current_password: this.currentPassword,
        new_password: this.newPassword,
        repeat_password: this.repeatPassword,
      };
      User.changePassword(data)
        .then((res) => {
          this.$store
            .dispatch('User/logoutUser');
          this.$router.push({
            name: 'login',
            params: {
              message: res.message,
            },
          });
        })
        .catch((err) => {
          this.serverError = err;
        });
    },
  },
};
</script>
