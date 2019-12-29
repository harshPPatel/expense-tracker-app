/* eslint-disable no-underscore-dangle */
/**
 * Function to use in array.sort to sort in descending order by date property
 * @param {*} a First Object
 * @param {*} b Second Object
 */
const sortByDate = (a, b) => {
  if (new Date(a.date) > new Date(b.date)) {
    return -1;
  }
  if (new Date(a.date) < new Date(b.date)) {
    return 1;
  }
  return 0;
};

/**
 * Maps and sorts the provided expenses and incomes returned from database
 * and removes unnessacary informations from docs
 * @param {Array} expenses Array of expenses returned from database
 * @param {Array} incomes Array of incomes returned from database
 * @returns {Array} Array of sorted statement entries
 */
const mapAndSort = (expenses, incomes) => {
  // mapping the expenses
  const mappedExpenses = expenses.map((expense) => {
    delete expense._doc.__v;
    return {
      ...expense._doc,
      type: 'expense',
    };
  });

  // mapping incomes
  const mappedIncomes = incomes.map((income) => {
    delete income._doc.__v;
    return {
      ...income._doc,
      type: 'expense',
    };
  });

  // sorting the statements
  const statements = [
    ...mappedExpenses,
    ...mappedIncomes,
  ].sort(sortByDate);

  return statements;
};

module.exports = {
  mapAndSort,
};
