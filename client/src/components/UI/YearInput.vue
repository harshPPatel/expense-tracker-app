<template>
  <div class="col-4">
    <select class="form-control" id="year"
      @change="updateStatements">
      <option
        v-for="year in 10"
        :key="year"
        :value="getYear(year)"
        :selected="getYear(year) === new Date().getFullYear()">
        {{ getYear(year) }}
      </option>
    </select>
  </div>
</template>

<script>
import Statement from '../../API/Statement';

export default {
  name: 'MonthInput',
  methods: {
    getYear(value) {
      const now = new Date();
      const year = now.getFullYear();
      return year - value + 1;
    },
    updateStatements(e) {
      const { value } = e.target;
      // updating month in store
      this.$store.commit('UI/setUIYear', value);
      // getting statements from store with updated values
      Statement.getStatementsByMonth()
        .then((res) => {
          // updating statements in store
          this.$store.commit('Statement/setStatements', res.statements);
        });
    },
  },
};
</script>
