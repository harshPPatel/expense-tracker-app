<template>
  <div class="container mt-4">
    <div class="row">
      <side-bar />
      <main role="main" class="col-md-9 ml-sm-auto px-4">
        <h1>Incomes</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <router-link to="/dashboard">Dashboard</router-link>
            </li>
            <li class="breadcrumb-item active">
              Incomes
            </li>
          </ol>
        </nav>
        <hr>
        <div class="card mb-4">
          <div class="card-header">
            Add Income
          </div>
          <div class="card-body">
            <!-- Add Income Form -->
            <h5 class="card-title">Create New Income</h5>
            <add-income-form />
          </div>
        </div>
      </main>
      <div class="col-12">
        <!-- Incomes List -->
        <h3 class="mb-4 mt-4">Your Incomes</h3>
        <income-list />
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from '../components/UI/SideBar.vue';
import AddIncomeForm from '../components/UI/forms/AddIncomeForm.vue';
import IncomeList from '../components/IncomeList.vue';
import Income from '../API/Income';

export default {
  name: 'Incomes',
  data: () => ({
    formDate: '',
  }),
  components: {
    SideBar,
    AddIncomeForm,
    IncomeList,
  },
  mounted() {
    // Getting all incomes from database
    Income.getAll()
      .then((res) => {
        // Adding all incomes to the store
        this.$store.commit('Income/setIncomes', res.incomes);
      });
  },
};
</script>
