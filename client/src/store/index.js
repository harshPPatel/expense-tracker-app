import Vue from 'vue';
import Vuex from 'vuex';

import UI from './modules/UI';
import User from './modules/User';
import Statement from './modules/Statement';
import Expense from './modules/Expense';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    UI,
    User,
    Statement,
    Expense,
  },
});
