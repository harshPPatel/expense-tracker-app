<template>
  <div
    class="alert alert-success alert-dismissible fade show"
    role="alert">
    {{ message }}
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
  name: 'SuccessAlert',
  props: ['message'],
};
</script>
