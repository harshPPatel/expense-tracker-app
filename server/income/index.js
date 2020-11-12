const { Router } = require('express');

const User = require('../models/User');
const Income = require('../models/Income');
const create = require('./create');
const update = require('./update');
const remove = require('./remove');
const validateIncome = require('../validators/expenseIncome');

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
  const incomes = await Income.find({
    username: req.username,
  })
    .sort({ date: -1 })
    .exec();

  // Sending the response to the user
  res.status(200);
  res.json({
    incomes,
    count: incomes.length,
    username: req.username,
  });
});

// Expense Create Route
router.post('/create', validateIncome.create, create);

// Expense Update Route
router.put('/update', validateIncome.update, update);

// Expense remove Route
router.delete('/:id', remove);

module.exports = router;
