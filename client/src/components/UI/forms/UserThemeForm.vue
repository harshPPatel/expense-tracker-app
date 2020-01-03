<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
      <error-alert
        v-if="serverError"
        :error="serverError" />
      <div class="form-group">
        <label for="theme">Your Theme</label>
        <select
          class="form-control"
          id="theme"
          v-model="theme"
          @change="updateTheme">
          <option v-for="(theme, index) in themes"
            :key="theme"
            :value="index">
            {{ theme }}
            </option>
        </select>
      </div>
    </div>
    <div class="col-lg-3"></div>
  </div>
</template>

<script>
import User from '../../../API/User';
import ErrorAlert from '../ErrorAlert.vue';

export default {
  name: 'UserThemeForm',
  components: {
    ErrorAlert,
  },
  data: () => ({
    theme: 0,
    themes: ['Default', 'Cyborg', 'Lux', 'United'],
    serverError: null,
  }),
  mounted() {
    this.theme = this.$store.state.User.settings.theme;
  },
  methods: {
    updateTheme() {
      this.serverError = null;
      User.updateTheme(this.theme)
        .then((res) => {
          this.$store.commit('User/setTheme', res.updatedTheme);
          const linkTag = document.querySelector('link[data-style="theme"]');
          linkTag.href = this.getThemeCSSLink(this.theme);
        })
        .catch((err) => {
          this.serverError = err;
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
