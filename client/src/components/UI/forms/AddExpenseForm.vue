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
          placeholder="Expense Title"
          minlength="3"
          required>
      </div>
      <div class="col">
        <input
          v-model="form.amount"
          type="number"
          class="form-control"
          placeholder="Expense Amount"
          min="0"
          required>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col">
        <datetime
          v-model="form.date"
          type="datetime"
          placeholder="Expense Date"
          class="form-control"
          required />
      </div>
      <div class="col">
        <button
          type="submit"
          class="btn btn-primary btn-block"
          :disabled="!form.title || !form.amount || !form.date || isLoading">
          {{ isLoading ? 'Adding Expense...' : 'Add Expense' }}
        </button>
      </div>
    </div>
  </form>
</template>

<script>
import { Datetime } from 'vue-datetime';

import ErrorAlert from '../ErrorAlert.vue';
import Expense from '../../../API/Expense';

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
      // Adding expense in database
      Expense.addExpense(this.form)
        .then((res) => {
          // adding expense to the store
          this.$store.commit('Expense/addExpense', res.expense);
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
