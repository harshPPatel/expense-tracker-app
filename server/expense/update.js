/* eslint-disable no-underscore-dangle */
const User = require('../models/User');
const Expense = require('../models/Expense');

/**
 * Handles update request for expense
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next netx function
 */
const update = async (req, res, next) => {
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
    _id: req.body._id.toString(),
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

  // updating the expense entry
  expense.title = req.body.title.toString();
  expense.amount = Number(req.body.amount.toString());
  expense.date = Date(req.body.date);

  // Saving expense to the database
  expense.save()
    .then((response) => {
      res.status(200);
      res.json({
        message: 'Expense updated successfully',
        updatedExpense: response,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = update;
