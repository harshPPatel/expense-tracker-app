const { compareSync } = require('bcrypt');

const User = require('../models/User');
const { generateToken } = require('../utils/token');

/**
 * Handles request on login route and responses the user with token
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next Next function
 */
const login = async (req, res, next) => {
  const user = {
    username: req.body.username.toString(),
    password: req.body.password.toString(),
  };

  // Finding user in database
  const dbUser = await User.findOne({ username: user.username }).exec();

  // Throwing error if user does not exists
  if (!dbUser) {
    res.status(404);
    next(new Error('Invalid Username or Password.'));
    return;
  }

  // Validating the password
  const isValidPassword = compareSync(user.password, dbUser.password);

  // Throwing the error if password is not valid
  if (!isValidPassword) {
    res.status(400);
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
    token: `Bearer ${await generateToken(user.username)}`,
    settings,
  };

  // Responding with token if user has valid credentials
  res.status(200);
  res.json(response);
};

module.exports = login;
