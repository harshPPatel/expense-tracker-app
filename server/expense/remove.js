/* eslint-disable no-underscore-dangle */
const User = require('../models/User');
const Expense = require('../models/Expense');

/**
 * Handles remove request for expense
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next netx function
 */
const remove = async (req, res, next) => {
  // Finding user in the database
  const user = await User.findOne({
    username: req.username,
  }).exec();

  // Throwing error if user does not exists
  if (!user) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // Finding expense in database
  const expense = await Expense.findOne({
    _id: req.params.id.toString(),
  }).exec();

  // Throwing error if expense not found
  if (!expense) {
    res.status(404);
    next(new Error('Expense not found'));
    return;
  }

  // Throwing error if expense does not belongs to logged in user
  if (expense.username !== req.username) {
    res.status(401);
    next(new Error('Unauthorized Access'));
    return;
  }

  // Saving expense to the database
  expense
    .remove()
    .then(() => {
      res.status(200);
      res.json({
        message: 'Expense removed successfully',
        removedExpenseId: expense._id,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = remove;
