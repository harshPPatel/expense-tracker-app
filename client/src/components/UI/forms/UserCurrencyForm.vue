<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
      <error-alert
        v-if="serverError"
        :error="serverError" />
      <div class="form-group">
        <label for="currency">Your Currency</label>
        <select
          class="form-control"
          id="currency"
          v-model="currency"
          @change="updateCurrency">
          <option v-for="currency in currencies"
            :key="currency"
            :value="currency">
            {{ currency.toString() }}
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
  name: 'UserCurrencyForm',
  components: {
    ErrorAlert,
  },
  data: () => ({
    currency: '$',
    currencies: ['$', '€', '₹', '£', '¥'],
    serverError: null,
  }),
  mounted() {
    this.currency = this.$store.state.User.settings.currency;
  },
  methods: {
    updateCurrency() {
      this.serverError = null;
      User.updateCurrency(this.currency)
        .then((res) => {
          this.$store.commit('User/setCurrency', res.updatedCurrency);
        })
        .catch((err) => {
          this.serverError = err;
        });
    },
  },
};
</script>
