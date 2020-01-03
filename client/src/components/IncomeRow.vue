<template>
  <tr>
    <th scope="row">{{ index + 1 }}</th>
    <td>{{ income.title }}</td>
    <td>{{ getFormattedCurrency() }}</td>
    <td>{{ getFormattedDate() }}</td>
    <td>
      <button type="button"
        class="btn btn-primary"
        data-toggle="modal"
        :data-target="`#editIncome-${ income._id }`">
        Edit
      </button>
    </td>
    <td>
      <button
        @click="removeIncome"
        type="button"
        class="btn btn-outline-danger">Delete</button>
    </td>
  </tr>
</template>

<script>
/* eslint-disable no-underscore-dangle */
import { mapState } from 'vuex';
import Income from '../API/Income';

export default {
  name: 'IncomeRow',
  props: ['income', 'index'],
  computed: mapState(['User']),
  methods: {
    getFormattedCurrency() {
      // returning the currency in formatted string
      const formattedAmount = this.income.amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
      return `${this.User.settings.currency} ${formattedAmount}`;
    },
    getFormattedDate() {
      // returning date in formated string
      const date = new Date(this.income.date);
      return date.toDateString();
    },
    removeIncome() {
      // removing the income from the database
      Income.removeIncome(this.income._id)
        .then((res) => {
          this.$store.dispatch('Income/removeIncome', res.removedIncomeId);
        });
    },
  },
};
</script>
