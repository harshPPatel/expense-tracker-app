<template>
  <div class="container mt-4">
    <div class="row">
      <side-bar />
      <main role="main" class="col-md-9 ml-sm-auto px-4">
        <h1>Statements</h1>
        <nav>
          <!-- bread crumbs -->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <router-link to="/dashboard">Dashboard</router-link>
            </li>
            <li class="breadcrumb-item active">
              Statements
            </li>
          </ol>
        </nav>
      </main>
      <div class="col-12">
        <div class="d-flex justify-content-between">
          <!-- Statements List -->
          <h3 class="mb-4 mt-4">Your Statements</h3>
          <form class="mb-4 mt-4">
            <div class="row">
              <!-- Month Select Input -->
              <label for="month" class="col-2 col-form-label">Month</label>
              <month-input />
              <!-- Year Select Input -->
              <label for="year" class="col-2 col-form-label">Year</label>
              <year-input />
            </div>
          </form>
        </div>
        <!-- Statement Lists -->
        <statement-list />
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from '../components/UI/SideBar.vue';
import MonthInput from '../components/UI/MonthInput.vue';
import YearInput from '../components/UI/YearInput.vue';
import StatementList from '../components/StatementList.vue';
import Statement from '../API/Statement';


export default {
  name: 'Statements',
  data: () => ({
    formDate: '',
  }),
  components: {
    SideBar,
    MonthInput,
    YearInput,
    StatementList,
  },
  mounted() {
    // Getting all statements from database
    Statement.getCurrent()
      .then((res) => {
        // Adding all expenses to the store
        this.$store.commit('Statement/setStatements', res.statements);
      });
  },
};
</script>
