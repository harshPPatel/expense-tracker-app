<template>
  <div class="container mt-4">
    <h1 class="text-center">User Settings</h1>
    <hr>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/dashboard">Dashboard</router-link>
        </li>
        <li class="breadcrumb-item active">
          User Settings
        </li>
      </ol>
    </nav>
    <user-quote-form />
    <user-theme-form />
    <user-currency-form />
    <user-expense-warning-form />
    <div class="row">
      <div class="col-md-3"></div>
      <div class="col-md-6">
        <hr>
        <h4 class="text-success">Security</h4>
        <hr>
        <user-change-password-form />
      </div>
      <div class="col-md-3"></div>
    </div>
    <div class="row">
      <div class="col-md-3"></div>
      <div class="col-md-6">
        <hr>
        <h4 class="text-danger">Danger Zone</h4>
        <hr>
        <h6>Default settings</h6>
        <p>
          By pressing, 'Change to Default' will set all settings to the default settings same as
          the settings when the account was created.
          <br>
          Please be careful. <i class="text-danger">You will not be able to Undo it.</i>
        </p>
        <p class="text-right">
          <button type="submit" class="btn btn-outline-warning" @click="setDefaultSettings">
            Change to Default
          </button>
        </p>
        <h6>Delete Account</h6>
        <p>
          By pressing, 'Delete Account' will delete your account as well as all your data
          <b>permanently!</b>
          <br>
          Please be careful. <i class="text-danger">You will not be able to Undo it.</i>
        </p>
        <p class="text-right">
          <button type="submit" class="btn btn-outline-danger" @click="deleteAccount">
            Delete Account
          </button>
        </p>
      </div>
      <div class="col-md-3"></div>
    </div>
  </div>
</template>

<script>
import UserQuoteForm from '../components/UI/forms/UserQuoteForm.vue';
import UserThemeForm from '../components/UI/forms/UserThemeForm.vue';
import UserCurrencyForm from '../components/UI/forms/UserCurrencyForm.vue';
import UserExpenseWarningForm from '../components/UI/forms/UserExpenseWarningForm.vue';
import UserChangePasswordForm from '../components/UI/forms/UserChangePasswordForm.vue';

import User from '../API/User';

export default {
  name: 'UserSettings',
  components: {
    UserQuoteForm,
    UserThemeForm,
    UserCurrencyForm,
    UserExpenseWarningForm,
    UserChangePasswordForm,
  },
  methods: {
    deleteAccount() {
      User.deleteAccount()
        .then((res) => {
          this.$store.dispatch('User/logoutUser');
          this.$router.push({
            name: 'login',
            params: {
              message: res.message,
            },
          });
        });
    },
    setDefaultSettings() {
      User.setDefaultSettings()
        .then((res) => {
          this.$store.commit('User/setTheme', res.defaultSettings.theme);
          this.$store.commit('User/setCurrency', res.defaultSettings.currency);
          this.$store.commit('User/setExpenseWarningLimit', res.defaultSettings.expenseWarningLimit);
          // Updating the theme
          const linkTag = document.querySelector('link[data-style="theme"]');
          linkTag.href = this.getThemeCSSLink(res.defaultSettings.theme);
        });
    },
    getThemeCSSLink(theme) {
      switch (theme) {
        case 3:
          return 'https://bootswatch.com/4/united/bootstrap.min.css';
        case 1:
          return 'https://bootswatch.com/4/cyborg/bootstrap.min.css';
        case 2:
          return 'https://bootswatch.com/4/lux/bootstrap.min.css';
        default:
          return 'https://bootswatch.com/4/cosmo/bootstrap.min.css';
      }
    },
  },
};
</script>
