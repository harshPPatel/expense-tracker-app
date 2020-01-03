<template>
  <div class="table-responsive-lg">
    <table class="table table-hover">
      <caption>List of Your Statements</caption>
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Title</th>
          <th scope="col">Amount</th>
          <th scope="col">Date</th>
          <th scope="col">Type</th>
        </tr>
      </thead>
      <tbody>
        <!-- Statements List -->
        <statement-row
          v-for="(statement, index) in statements"
          :key="statement._id"
          :index="index"
          :statement="statement" />
        <!-- No statements Found Case -->
        <tr v-if="!statements || statements.length === 0">
          <th colspan="5" class="text-center">
            No Statements Found! 🙀
          </th>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import StatementRow from './StatementRow.vue';

export default {
  name: 'StatementList',
  components: {
    StatementRow,
  },
  computed: mapGetters({
    statements: 'Statement/getStatements',
  }),
};
</script>
