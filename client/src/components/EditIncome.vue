<template>
  <div class="modal fade"
    :id="`editIncome-${ income._id }`"
    tabindex="-1"
    role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit Income</h5>
          <!-- modal close button -->
          <button type="button"
            class="close"
            :id="`editIncomeClose-${ income._id }`"
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
            <label :for="`title${ income._id }`">Title</label>
            <input
              v-model="form.title"
              name="title"
              type="text"
              class="form-control"
              placeholder="Income Title"
              minlength="3"
              :id="`title${ income._id }`"
              required>
          </div>
          <div class="form-group">
            <label :for="`amount${ income._id }`">Amount</label>
            <input
              v-model="form.amount"
              type="number"
              class="form-control"
              placeholder="Income Amount"
              min="0"
              :id="`amount${ income._id }`"
              required>
          </div>
          <div class="form-group">
            <label :for="`date${ income._id }`">Date</label>
            <datetime
              v-model="form.date"
              type="datetime"
              placeholder="Income Date"
              class="form-control"
              :inputId="`date${ income._id }`"
              required />
          </div>
          <button
            @click.prevent="submitForm"
            type="submit"
            class="btn btn-primary btn-block"
            :disabled="!form.title || !form.amount || !form.date || !form._id || isLoading">
            {{ isLoading ? 'Updating Income...' : 'Update' }}
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
import Income from '../API/Income';

export default {
  name: 'EditIncome',
  props: ['income'],
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
    if (this.income) {
      this.form.title = this.income.title;
      this.form.amount = this.income.amount;
      this.form.date = this.income.date;
      this.form._id = this.income._id;
    }
  },
  methods: {
    submitForm() {
      this.isLoading = true;
      // updating the income in the database
      Income.updateIncome(this.form)
        .then((res) => {
          // updating the income in the store
          this.$store.commit('Income/updateIncome', res.updatedIncome);
          // closing the modal
          const closeButton = document.getElementById(`editIncomeClose-${this.income._id}`);
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
