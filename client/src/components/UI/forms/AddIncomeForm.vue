<template>
  <form @submit.prevent="submitForm">
    <error-alert v-if="serverError" :error="serverError" />
    <div class="row">
      <div class="col">
        <input
          v-model="form.title"
          name="title"
          type="text"
          class="form-control"
          placeholder="Income Title"
          minlength="3"
          required>
      </div>
      <div class="col">
        <input
          v-model="form.amount"
          type="number"
          class="form-control"
          placeholder="Income Amount"
          min="0"
          required>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col">
        <datetime
          v-model="form.date"
          type="datetime"
          placeholder="Income Date"
          class="form-control"
          required />
      </div>
      <div class="col">
        <button
          type="submit"
          class="btn btn-primary btn-block"
          :disabled="!form.title || !form.amount || !form.date || isLoading">
          {{ isLoading ? 'Adding Income...' : 'Add Income' }}
        </button>
      </div>
    </div>
  </form>
</template>

<script>
import { Datetime } from 'vue-datetime';

import ErrorAlert from '../ErrorAlert.vue';
import Income from '../../../API/Income';

export default {
  name: 'AddExpenseForm',
  components: {
    Datetime,
    ErrorAlert,
  },
  data: () => ({
    form: {
      title: '',
      amount: '',
      date: '',
    },
    isLoading: false,
    serverError: null,
  }),
  methods: {
    submitForm() {
      this.isLoading = true;
      // Adding income in database
      Income.addIncome(this.form)
        .then((res) => {
          // adding income to the store
          this.$store.commit('Income/addIncome', res.income);
        })
        .catch((err) => { this.serverError = err; });
      this.isLoading = false;
    },
  },
};
</script>

<style>
.vdatetime-input {
  border: 0;
  width: 100%;
  height: 100%;
}
</style>
