/* eslint-disable no-param-reassign */
/* eslint-disable no-underscore-dangle */

const state = {
  incomes: [],
};

const mutations = {
  setIncomes(storeState, value) {
    storeState.incomes = value;
  },
  addIncome(storeState, value) {
    storeState.incomes.unshift(value);
  },
  updateIncome(storeState, value) {
    let incomeIndex = '';
    // finding index of expense to be updated
    storeState.incomes.forEach((element, index) => {
      if (element._id === value._id) {
        incomeIndex = index;
      }
    });
    // Updating expense values
    storeState.incomes[incomeIndex].title = value.title;
    storeState.incomes[incomeIndex].amount = value.amount;
    storeState.incomes[incomeIndex].date = value.date;
    storeState.incomes[incomeIndex].modifiedAt = value.modifiedAt;
  },
};

const getters = {
  getIncomes(storeState) {
    return storeState.incomes;
  },
};

const actions = {
  removeIncome(context, id) {
    // removing expense from store
    const updatedIncomes = context.state.incomes
      .filter(income => income._id !== id);
    context.commit('setIncomes', updatedIncomes);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
