<template>
  <div class="container mt-4">
    <div class="row">
      <side-bar />
      <main role="main" class="col-md-9 ml-sm-auto px-4">
        <h1><span class="small">Hello,</span> {{ User.username.toUpperCase() }}!</h1>
        <hr>
        <div class="row mt-4">
          <div class="col-md-6 col-12">
            <div class="card p-3">
              <chart
                v-if="Statement.totalExpense"
                class="chart-container" />
            </div>
          </div>
          <div class="col-md-6 col-12">
            <!-- make component -->
            <dashboard-limit-card />
          </div>
        </div>
        <br>
        <br>
        <!-- Make separate Component -->
        <dashboard-quote />
        <hr>
        <div class="mt-4">
          <dashboard-add-info-card type="Expenses" />
          <dashboard-add-info-card type="Incomes" />
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import SideBar from '../components/UI/SideBar.vue';
import Chart from '../components/UI/Chart.vue';
import DashboardLimitCard from '../components/UI/DashboardLimitCard.vue';
import DashboardQuote from '../components/UI/DashboardQuote.vue';
import DashboardAddInfoCard from '../components/UI/DashboardAddInfoCard.vue';
import Statement from '../API/Statement';

export default {
  name: 'Dashboard',
  components: {
    SideBar,
    Chart,
    DashboardLimitCard,
    DashboardQuote,
    DashboardAddInfoCard,
  },
  computed: mapState(['User', 'Statement']),
  mounted() {
    Statement.getCurrent(this.$store.state.User.token)
      .then((res) => {
        this.$store.commit('Statement/setStatements', res.statements);
        this.$store.commit('Statement/setTotalExpense', res.totalExpense);
        this.$store.commit('Statement/setTotalIncome', res.totalIncome);
        // Updating the theme
        const linkTag = document.querySelector('link[data-style="theme"]');
        linkTag.href = this.getThemeCSSLink(this.User.settings.theme);
      });
  },
  methods: {
    getThemeCSSLink(theme) {
      switch (theme) {
        case 3:
          return 'https://bootswatch.com/4/united/bootstrap.min.css';
        case 1:
          return 'https://bootswatch.com/4/cyborg/bootstrap.min.css';
        case 2:
          return 'https://bootswatch.com/4/lux/bootstrap.min.css';
        default:
          return 'https://bootswatch.com/4/cosmo/bootstrap.min.css';
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.chart-container,
.special-card {
  padding: 0;
  height: 250px;
  width: 250px;
}

.special-card {
  width: 100% !important;
}
</style>
