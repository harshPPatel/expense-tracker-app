<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
      <error-alert
        v-if="serverError"
        :error="serverError" />
      <div class="form-group">
        <label for="expenseWarning">Your Expesnse Warning Limit</label>
        <input type="number"
          class="form-control"
          id="expenseWarning"
          placeholder="Expense Warning Limit"
          v-model="expenseWarningLimit" />
      </div>
      <div class="form-group text-right">
        <button type="submit" class="btn btn-primary" @click="updateExpenseWarningLimit">
          Update
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
  name: 'UserCurrencyForm',
  components: {
    ErrorAlert,
  },
  data: () => ({
    expenseWarningLimit: 1000,
    serverError: null,
  }),
  mounted() {
    this.expenseWarningLimit = this.$store.state.User.settings.expenseWarningLimit;
  },
  methods: {
    updateExpenseWarningLimit() {
      this.serverError = null;
      User.updateExpenseWarningLimit(this.expenseWarningLimit)
        .then((res) => {
          this.$store
            .commit('User/setExpenseWarningLimit', res.updatedExpenseWarningLimit);
        })
        .catch((err) => {
          this.serverError = err;
        });
    },
  },
};
</script>
