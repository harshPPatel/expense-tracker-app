<template>
  <div class="table-responsive-lg">
    <table class="table table-hover">
      <caption>List of Your Incomes</caption>
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Title</th>
          <th scope="col">Amount</th>
          <th scope="col">Date</th>
          <th scope="col">Edit</th>
          <th scope="col">Delete</th>
        </tr>
      </thead>
      <tbody>
        <!-- Incomes List -->
        <income-row
          v-for="(income, index) in incomes"
          :key="income._id"
          :index="index"
          :income="income" />
      </tbody>
    </table>
    <!-- Edit income modals -->
    <edit-income
      v-for="(income, index) in incomes"
      :key="`editIncome-${income._id}`"
      :index="index"
      :income="income" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import IncomeRow from './IncomeRow.vue';
import EditIncome from './EditIncome.vue';

export default {
  name: 'ExpenseList',
  components: {
    IncomeRow,
    EditIncome,
  },
  computed: mapGetters({
    incomes: 'Income/getIncomes',
  }),
};
</script>
