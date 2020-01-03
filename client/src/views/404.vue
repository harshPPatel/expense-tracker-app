<template>
  <div class="container mt-5">
    <div class="text-center">
      <h1 class="text-danger display-2">404</h1>
      <h2>
        Request page not found!
        <br />
        Please check the URL again.
      </h2>
    </div>
    <p class="text-center mt-4">
      <button class="btn btn-outline-primary mr-4"
        @click="goBack">Go Back</button>
      <router-link to="/" exact class="btn btn-primary mr-4">
        Go to Home Page
      </router-link>
    </p>
  </div>
</template>

<script>
export default {
  name: 'NotFoundPage',
  methods: {
    goBack() {
      this.$router.back();
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 60px;
}
</style>
