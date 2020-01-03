/* eslint-disable no-param-reassign */
const state = {
  user: {
    username: '',
    password: '',
    confirmPassword: '',
  },
  statements: {
    month: 0,
    year: new Date().getFullYear(),
  },
};

const mutations = {
  setUIUsername(store, value) {
    store.user.username = value;
  },
  setUIPassword(store, value) {
    store.user.password = value;
  },
  setUIConfirmPassword(store, value) {
    store.user.confirmPassword = value;
  },
  setUIMonth(store, value) {
    store.statements.month = value;
  },
  setUIYear(store, value) {
    store.statements.year = value;
  },
};

const getters = {

};

const actions = {
  clearUIUser({ commit }) {
    commit('setUIUsername', '');
    commit('setUIPassword', '');
    commit('setUIConfirmPassword', '');
  },
  clearUIUserPassword({ commit }) {
    commit('setUIPassword', '');
    commit('setUIConfirmPassword', '');
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
