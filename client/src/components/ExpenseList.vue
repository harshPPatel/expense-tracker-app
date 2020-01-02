<template>
  <div class="table-responsive-lg">
    <table class="table table-hover">
      <caption>List of Your Expenses</caption>
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
        <!-- Expenses List -->
        <expense-row
          v-for="(expense, index) in expenses"
          :key="expense._id"
          :index="index"
          :expense="expense" />
      </tbody>
    </table>
    <!-- Edit expense modals -->
    <edit-expense
      v-for="(expense, index) in expenses"
      :key="`editExpense-${expense._id}`"
      :index="index"
      :expense="expense" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import ExpenseRow from './ExpenseRow.vue';
import EditExpense from './EditExpense.vue';

export default {
  name: 'ExpenseList',
  components: {
    ExpenseRow,
    EditExpense,
  },
  computed: mapGetters({
    expenses: 'Expense/getExpenses',
  }),
};
</script>
