/* eslint-disable no-param-reassign */
const state = {
  statements: [],
  totalExpense: 0,
  totalIncome: 0,
};

const mutations = {
  setStatements(storeState, value) {
    storeState.statements = value;
  },
  setTotalExpense(storeState, value) {
    storeState.totalExpense = value;
  },
  setTotalIncome(storeState, value) {
    storeState.totalIncome = value;
  },
};

const getters = {
  getStatements(storeState) {
    return storeState.statements;
  },
  getTotalExpense(storeState) {
    return storeState.totalExpense;
  },
  getTotalIncome(storeState) {
    return storeState.totalIncome;
  },
};

const actions = {
  // loginUser({ commit }, value) {
  //   commit('setUsername', value.username);
  //   commit('setToken', value.token);
  //   commit('setUserLoggedIn');
  // },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
