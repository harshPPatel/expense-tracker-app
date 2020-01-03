<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
      <error-alert
        v-if="serverError"
        :error="serverError" />
      <div class="form-group">
        <label for="quote">Your Quote</label>
        <textarea
          class="form-control"
          id="quote"
          rows="3"
          v-model="quote"
          minlength="5"></textarea>
      </div>
      <div class="form-group text-right">
        <button type="submit" class="btn btn-secondary mr-4" @click="updateQuoteToRandom">
          Set Random Quote
        </button>
        <button type="submit" class="btn btn-primary" @click="updateQuote">Update</button>
      </div>
    </div>
    <div class="col-lg-3"></div>
  </div>
</template>

<script>
import User from '../../../API/User';
import ErrorAlert from '../ErrorAlert.vue';

export default {
  name: 'UserQuoteForm',
  components: {
    ErrorAlert,
  },
  data: () => ({
    quote: '',
    serverError: null,
  }),
  mounted() {
    this.quote = this.$store.state.User.settings.quote;
  },
  methods: {
    updateQuote() {
      this.serverError = null;
      const options = {
        quote: this.quote.toString().trim(),
        isRandom: false,
      };
      User.updateQuote(options)
        .then((res) => {
          this.$store.commit('User/setQuote', res.updatedQuote);
          this.quote = res.updatedQuote;
        })
        .catch((err) => {
          this.serverError = err;
        });
    },
    updateQuoteToRandom() {
      this.serverError = null;
      const options = {
        isRandom: true,
      };
      User.updateQuote(options)
        .then((res) => {
          this.$store.commit('User/setQuote', res.updatedQuote);
        })
        .catch((err) => {
          this.serverError = err;
        });
    },
  },
};
</script>
