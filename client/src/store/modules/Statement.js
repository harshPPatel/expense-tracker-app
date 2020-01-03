/* eslint-disable no-param-reassign */
const state = {
  statements: [],
  totalExpense: 0,
  totalIncome: 0,
  minimumYear: null,
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
  setMinimumYear(storeState, value) {
    storeState.minimumYear = value;
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

};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
