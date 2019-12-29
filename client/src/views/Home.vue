<template>
  <div class="jumbotron text-center">
    <div class="container">
      <h1 class="display-4">Expense Tracker App</h1>
      <p class="lead">
        Track your expenses and incomes in one app! Login and get access to all of your
        expenses and incomes. App also features the statements!
      </p>
      <hr class="my-4">
      <p class="lead">
        So What are you waiting for!? <b>Sign Up</b> Now!
      </p>
      <router-link to="/about" role="button"
        class="btn btn-secondary btn-lg mr-4">
        About App
      </router-link>
      <router-link to="/signup" role="button"
        class="btn btn-primary btn-lg">
        Sign Up
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
};
</script>
