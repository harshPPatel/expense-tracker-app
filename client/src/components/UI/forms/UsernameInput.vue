<template>
  <div class="form-group">
    <label for="username">Username</label>
    <input
      @input="validateUsername"
      :value="UI.user.username"
      type="text"
      class="form-control"
      id="username"
      placeholder="Username"
      :class="{
        'is-invalid': error,
        'is-valid': (UI.user.username && !error)
      }">
    <div class="text-danger">
      {{ error }}
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'UsernameInput',
  data: () => ({
    error: '',
  }),
  computed: mapState(['UI']),
  methods: {
    validateUsername(e) {
      // gets username from input
      const username = e.target.value.trim();
      // regex for username validation
      const usernameRegex = /(^[a-zA-Z0-9_]+$)/;

      // Validating the username
      if (username === '') {
        this.error = 'Username is required';
      } else if (!username.match(usernameRegex)) {
        this.error = 'Username can contain only following characters: A to Z, a to z, 0 to 9 or _.';
      } else {
        // saving username to the store if it is valid
        this.$store.commit('UI/setUIUsername', username);
        this.error = '';
      }
    },
  },
};
</script>
