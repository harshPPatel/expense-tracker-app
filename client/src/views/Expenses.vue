<template>
  <div class="container mt-4">
    <div class="row">
      <side-bar />
      <main role="main" class="col-md-9 ml-sm-auto px-4">
        <h1>Expenses</h1>
        <hr>
        <div class="card mb-4">
          <div class="card-header">
            Add Expense
          </div>
          <div class="card-body">
            <!-- Add Expense Form -->
            <h5 class="card-title">Create New Expense</h5>
            <add-expense-form />
          </div>
        </div>
      </main>
      <div class="col-12">
        <!-- Expenses List -->
        <h3 class="mb-4 mt-4">Your Expenses</h3>
        <expense-list />
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from '../components/UI/SideBar.vue';
import AddExpenseForm from '../components/UI/forms/AddExpenseForm.vue';
import ExpenseList from '../components/ExpenseList.vue';
import Expense from '../API/Expense';

export default {
  name: 'Expenses',
  data: () => ({
    formDate: '',
  }),
  components: {
    SideBar,
    AddExpenseForm,
    ExpenseList,
  },
  mounted() {
    // Getting all expenses from database
    Expense.getAll()
      .then((res) => {
        // Adding all expenses to the store
        this.$store.commit('Expense/setExpenses', res.expenses);
      });
  },
};
</script>
