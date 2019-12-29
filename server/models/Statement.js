const { lastDayOfMonth } = require('date-fns');
const Expense = require('../models/Expense');
const Income = require('../models/Income');

/**
 * Returns expenses and incomes for provided options
 * @param {*} options month, year and username
 */
const getStatements = async (options) => {
  // new date
  const date = new Date();
  // Getting month and year
  const month = options.month
    ? options.month - 1
    : date.getMonth();
  const year = options.year
    ? options.year
    : date.getFullYear();
  // limit days for fetching results from database
  const firstDay = new Date(year, month, 1);
  const lastDay = lastDayOfMonth(firstDay);

  // Expenses
  const expenses = await Expense
    .find({
      username: options.username,
      date: {
        $gte: firstDay,
        $lte: lastDay,
      },
    }).exec();

  // Incomes
  const incomes = await Income
    .find({
      username: options.username,
      date: {
        $gte: firstDay,
        $lte: lastDay,
      },
    }).exec();

  return {
    expenses,
    incomes,
  };
};

module.exports = {
  getStatements,
};
