const User = require('../models/User');
const Statement = require('../models/Statement');
const statementUtils = require('../utils/statements');

/**
 * Handles the index route for the statements
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const current = async (req, res, next) => {
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

  // options for statemets requests
  const options = {
    month: null,
    year: null,
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
};

module.exports = current;
