<template>
  <tr>
    <th scope="row">{{ index + 1 }}</th>
    <td>{{ statement.title }}</td>
    <td>{{ getFormattedCurrency() }}</td>
    <td>{{ getFormattedDate() }}</td>
    <td><b>{{ statement.type }}</b></td>
  </tr>
</template>

<script>
/* eslint-disable no-underscore-dangle */
import { mapState } from 'vuex';

export default {
  name: 'StatementRow',
  props: ['statement', 'index'],
  computed: mapState(['User']),
  methods: {
    getFormattedCurrency() {
      // returning the currency in formatted string
      const formattedAmount = this.statement.amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
      return `${this.User.settings.currency} ${formattedAmount}`;
    },
    getFormattedDate() {
      // returning date in formated string
      const date = new Date(this.statement.date);
      return date.toDateString();
    },
  },
};
</script>
