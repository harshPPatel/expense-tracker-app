<template>
  <div class="card mt-4">
    <h5 class="card-header">
      <b>Your {{ type }}
      </b>
    </h5>
    <div class="card-body">
      <p class="card-text">
        Create, modify and delete your {{ type }} and keep track of them!
      </p>
      <router-link class="btn btn-primary"
        :to="type === 'Expenses'
          ? '/expenses'
          : '/incomes'">
        Add {{ type }}
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DashboardAddInfoCard',
  props: ['type'],
};
</script>
