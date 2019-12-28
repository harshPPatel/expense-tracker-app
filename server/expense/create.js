const User = require('../models/User');
const Expense = require('../models/Expense');

/**
 * Handles create request for expense
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next netx function
 */
const create = (req, res, next) => {
  // Finding user in the database
  const user = User.findOne({
    username: req.username,
  }).exec();

  // Throwing error if user does not exists
  if (!user) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // creating the expense entry
  const expense = new Expense({
    title: req.body.title.toString(),
    amount: Number(req.body.amount.toString()),
    date: Date(req.body.date),
    username: req.username,
  });

  // Saving expense to the database
  expense.save()
    .then((response) => {
      res.status(200);
      res.json({
        message: 'Expense created successfully',
        expense: response,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = create;
