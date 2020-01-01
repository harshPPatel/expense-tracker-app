<template>
  <div class="mt-4 text-center">
    <h1 class="mt-4">Your Quote</h1>
    <hr>
    <p class="lead">{{ User.settings.quote }}</p>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'DashboardQuote',
  computed: mapState(['User']),
};
</script>
