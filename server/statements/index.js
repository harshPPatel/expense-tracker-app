const { Router } = require('express');

const User = require('../models/User');
const Statement = require('../models/Statement');
const statementUtils = require('../utils/statements');

const router = Router();

// Index routes
router.get('/', async (req, res, next) => {
  // Finding the user in database
  const user = User.findOne({
    username: req.username,
  });

  // Throwing the error if user is not found
  if (!user) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // Getting month and year from query parameters
  const { month } = req.query;
  const { year } = req.query;
  console.log(month);
  // options for statemets requests
  const options = {
    month,
    year,
    username: req.username,
  };

  // Getting statements
  const statement = await Statement.getStatements(options);

  // mapping and sorting the statements
  const statements = statementUtils.mapAndSort(
    statement.expenses,
    statement.incomes,
  );

  // Responsing to the user
  res.status(200);
  res.json({
    statements,
    count: statements.length,
    username: req.username,
  });
});

module.exports = router;
