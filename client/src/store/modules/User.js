/* eslint-disable no-param-reassign */
const state = {
  username: '',
  isLoggedIn: false,
  token: '',
  settings: {
    quote: '',
    theme: 0,
    currency: '$',
    expenseWarningLimit: 1000,
  },
};

const mutations = {
  setUsername(storeState, value) {
    storeState.username = value;
  },
  setToken(storeState, value) {
    localStorage.token = value;
    storeState.token = value;
  },
  setUserLoggedIn(storeState) {
    storeState.isLoggedIn = true;
  },
  setUserLoggedOut(storeState) {
    storeState.isLoggedIn = false;
  },
  setQuote(storeState, value) {
    storeState.settings.quote = value;
  },
  setTheme(storeState, value) {
    storeState.settings.theme = value;
  },
  setCurrency(storeState, value) {
    storeState.settings.currency = value;
  },
  setExpenseWarningLimit(storeState, value) {
    storeState.settings.expenseWarningLimit = value;
  },
};

const getters = {
  isUserLoggedIn(storeState) {
    return storeState.isLoggedIn;
  },
};

const actions = {
  loginUser({ commit }, value) {
    commit('setUsername', value.username);
    commit('setToken', value.token);
    commit('setUserLoggedIn');
  },
  logoutUser({ commit }) {
    commit('setUsername', '');
    commit('setToken', '');
    commit('setUserLoggedOut');
  },
  setUserSettings({ commit }, value) {
    commit('setQuote', value.quote);
    commit('setTheme', value.theme);
    commit('setCurrency', value.currency);
    commit('setExpenseWarningLimit', value.expenseWarningLimit);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
