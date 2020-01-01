<template>
  <nav class="nav nav-tabs flex-column col-md-3 d-none d-md-block border pr-0">
    <router-link to="/dashboard" class="nav-link">Dashboard</router-link>
    <router-link to="/expenses" class="nav-link">Expenses</router-link>
    <router-link to="/incomes" class="nav-link">Incomes</router-link>
    <router-link to="/statements" class="nav-link">Statements</router-link>
  </nav>
</template>

<script>
export default {
  name: 'SideBar',
};
</script>

<style>
.nav-link.router-link-exact-active {
  color: rgba(0, 0, 0, 0.5);
  font-weight: bold;
}
</style>
