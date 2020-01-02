<template>
  <tr>
    <th scope="row">{{ index + 1 }}</th>
    <td>{{ expense.title }}</td>
    <td>{{ getFormattedCurrency() }}</td>
    <td>{{ getFormattedDate() }}</td>
    <td>
      <button type="button"
        class="btn btn-primary"
        data-toggle="modal"
        :data-target="`#editExpense-${ expense._id }`">
        Edit
      </button>
    </td>
    <td>
      <button
        @click="removeExpense"
        type="button"
        class="btn btn-outline-danger">Delete</button>
    </td>
  </tr>
</template>

<script>
/* eslint-disable no-underscore-dangle */
import { mapState } from 'vuex';
import Expense from '../API/Expense';

export default {
  name: 'ExpenseRow',
  props: ['expense', 'index'],
  computed: mapState(['User']),
  methods: {
    getFormattedCurrency() {
      // returning the currency in formatted string
      const formattedAmount = this.expense.amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
      return `${this.User.settings.currency} ${formattedAmount}`;
    },
    getFormattedDate() {
      // returning date in formated string
      const date = new Date(this.expense.date);
      return date.toDateString();
    },
    removeExpense() {
      // removing the expense from the database
      Expense.removeExpense(this.expense._id)
        .then((res) => {
          this.$store.dispatch('Expense/removeExpense', res.removedExpenseId);
        });
    },
  },
};
</script>
