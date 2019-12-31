const User = require('../models/User');

/**
 * Handles request on token route and responses the user with settings and response
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next Next function
 */
const login = async (req, res, next) => {
  // Finding user in database
  const dbUser = await User.findOne({ username: req.username }).exec();

  // Throwing error if user does not exists
  if (!dbUser) {
    res.status(404);
    next(new Error('Invalid Username or Password.'));
    return;
  }

  const settings = {
    quote: dbUser.quote,
    theme: dbUser.theme,
    currency: dbUser.currency,
    expenseWarningLimit: dbUser.expenseWarningLimit,
  };

  const response = {
    username: dbUser.username,
    isValidToken: true,
    settings,
  };

  // Responding with token if user has valid credentials
  res.status(200);
  res.json(response);
};

module.exports = login;
