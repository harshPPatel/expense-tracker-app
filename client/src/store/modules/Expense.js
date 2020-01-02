/* eslint-disable no-param-reassign */
/* eslint-disable no-underscore-dangle */

const state = {
  expenses: [],
};

const mutations = {
  setExpenses(storeState, value) {
    storeState.expenses = value;
  },
  addExpense(storeState, value) {
    storeState.expenses.unshift(value);
  },
  updateExpense(storeState, value) {
    let expenseIndex = '';
    // finding index of expense to be updated
    storeState.expenses.forEach((element, index) => {
      if (element._id === value._id) {
        expenseIndex = index;
      }
    });
    // Updating expense values
    storeState.expenses[expenseIndex].title = value.title;
    storeState.expenses[expenseIndex].amount = value.amount;
    storeState.expenses[expenseIndex].date = value.date;
    storeState.expenses[expenseIndex].modifiedAt = value.modifiedAt;
  },
};

const getters = {
  getExpenses(storeState) {
    return storeState.expenses;
  },
};

const actions = {
  removeExpense(context, id) {
    // removing expense from store
    const updatedExpenses = context.state.expenses
      .filter(expense => expense._id !== id);
    context.commit('setExpenses', updatedExpenses);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
  actions,
};
