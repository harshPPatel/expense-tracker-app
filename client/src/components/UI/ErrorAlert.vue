<template>
  <div
    class="alert alert-danger alert-dismissible fade show"
    role="alert">
    <strong>{{ error.errorCode }}</strong>
    <br>
    {{ error.message }}
    <button
      type="button"
      class="close"
      data-dismiss="alert">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
</template>

<script>
export default {
  name: 'ErrorAlert',
  props: ['error'],
};
</script>
