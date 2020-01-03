<template>
  <div class="card special-card text-center" style="width: 18rem;">
    <div class="card-body">
      <h5 class="card-title">
        Your Expenses
      </h5>
      <p class="card-text">
        {{ Statement.totalExpense > User.settings.expenseWarningLimit
          ? 'You are spending too much!'
          : 'Going good! Keep it up.' }}
      </p>
      <br>
      <h1>
        <b :class="`${ Statement.totalExpense > User.settings.expenseWarningLimit
          ? 'text-danger'
          : 'text-primary' }`">
          {{ Statement.totalExpense }}
        </b> / <span class="small">
          {{ User.settings.expenseWarningLimit }}
          </span>
      </h1>
      <br>
      <router-link to="/expenses" class="btn btn-outline-primary">Expenses</router-link>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'DashboardLimitCard',
  computed: mapState(['Statement', 'User']),
};
</script>
