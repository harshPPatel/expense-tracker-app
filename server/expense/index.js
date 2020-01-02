const { Router } = require('express');

const User = require('../models/User');
const Expense = require('../models/Expense');
const create = require('./create');
const update = require('./update');
const remove = require('./remove');
const validateExpense = require('../validators/expenseIncome');

const router = Router();

// index route
router.get('/', async (req, res, next) => {
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

  // Finding all expenses of user
  const expenses = await Expense
    .find({
      username: req.username,
    })
    .sort({ date: -1 })
    .exec();

  // Sending the response to the user
  res.status(200);
  res.json({
    expenses,
    count: expenses.length,
    username: req.username,
  });
});

// Expense Create Route
router.post('/create', validateExpense.create, create);

// Expense Update Route
router.put('/update', validateExpense.update, update);

// Expense remove Route
router.delete('/delete', validateExpense.remove, remove);

module.exports = router;
