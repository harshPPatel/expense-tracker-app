<template>
  <div class="col-4">
    <select class="form-control" id="month"
      @change="updateStatements">
      <option
        v-for="(month, index) in months"
        :key="month"
        :value="(index + 1)"
        :selected="month === new Date().getMonth().toString()">
        {{ month }}
      </option>
    </select>
  </div>
</template>

<script>
import Statement from '../../API/Statement';

export default {
  name: 'MonthInput',
  data: () => ({
    months: [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ],
  }),
  methods: {
    updateStatements(e) {
      const { value } = e.target;
      // udpating month value in store
      this.$store.commit('UI/setUIMonth', value);
      // getting statements for updated values
      Statement.getStatementsByMonth()
        .then((res) => {
          // updating statemnts in the store
          this.$store.commit('Statement/setStatements', res.statements);
        });
    },
  },
};
</script>
