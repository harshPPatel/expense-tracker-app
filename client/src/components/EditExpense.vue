<template>
  <div class="modal fade"
    :id="`editExpense-${ expense._id }`"
    tabindex="-1"
    role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit Expense</h5>
          <!-- modal close button -->
          <button type="button"
            class="close"
            :id="`editExpenseClose-${ expense._id }`"
            data-dismiss="modal"
            aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <!-- error alert -->
          <error-alert
            v-if="serverError"
            :error="serverError" />
          <div class="form-group">
            <label :for="`title${ expense._id }`">Title</label>
            <input
              v-model="form.title"
              name="title"
              type="text"
              class="form-control"
              placeholder="Expense Title"
              minlength="3"
              :id="`title${ expense._id }`"
              required>
          </div>
          <div class="form-group">
            <label for="amount">Amount</label>
            <input
              v-model="form.amount"
              type="number"
              class="form-control"
              placeholder="Expense Amount"
              min="0"
              :id="`amount${ expense._id }`"
              required>
          </div>
          <div class="form-group">
            <label for="date">Date</label>
            <datetime
              v-model="form.date"
              type="datetime"
              placeholder="Expense Date"
              class="form-control"
              :inputId="`date${ expense._id }`"
              required />
          </div>
          <button
            @click.prevent="submitForm"
            type="submit"
            class="btn btn-primary btn-block"
            :disabled="!form.title || !form.amount || !form.date || !form._id || isLoading">
            {{ isLoading ? 'Updating Expense...' : 'Update' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable no-underscore-dangle */
import { Datetime } from 'vue-datetime';

import ErrorAlert from './UI/ErrorAlert.vue';
import Expense from '../API/Expense';

export default {
  name: 'EditExpense',
  props: ['expense'],
  components: {
    Datetime,
    ErrorAlert,
  },
  data: () => ({
    form: {
      title: '',
      amount: 0,
      date: '',
      _id: '',
    },
    isLoading: false,
    serverError: null,
  }),
  mounted() {
    // adding values to local state if prop is passed
    if (this.expense) {
      this.form.title = this.expense.title;
      this.form.amount = this.expense.amount;
      this.form.date = this.expense.date;
      this.form._id = this.expense._id;
    }
  },
  methods: {
    submitForm() {
      this.isLoading = true;
      // updating the expense in the database
      Expense.updateExpense(this.form)
        .then((res) => {
          // updating the expense in the store
          this.$store.commit('Expense/updateExpense', res.updatedExpense);
          // closing the modal
          const closeButton = document.getElementById(`editExpenseClose-${this.expense._id}`);
          closeButton.click();
        })
        .catch((err) => {
          // adding the error to serverError
          this.serverError = err;
        });
      this.isLoading = false;
    },
  },
};
</script>
