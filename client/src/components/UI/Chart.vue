<script>
import { Pie } from 'vue-chartjs';

export default {
  extends: Pie,
  props: ['chartdata', 'options'],
  mounted() {
    this.renderChart({
      labels: ['Expenses', 'Incomes'],
      datasets: [
        {
          label: 'Expenses & Incomes',
          backgroundColor: [
            'red',
            'lightblue',
          ],
          data: [
            this.$store.state.Statement.totalExpense,
            this.$store.state.Statement.totalIncome,
          ],
        },
      ],
    });
  },
};
</script>
