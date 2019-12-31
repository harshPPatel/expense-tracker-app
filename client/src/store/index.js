import Vue from 'vue';
import Vuex from 'vuex';

import UI from './modules/UI';
import User from './modules/User';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    UI,
    User,
  },
});
