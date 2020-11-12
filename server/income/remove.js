/* eslint-disable no-underscore-dangle */
const User = require('../models/User');
const Income = require('../models/Income');

/**
 * Handles remove request for income
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
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

  // Finding income in database
  const income = await Income.findOne({
    _id: req.params.id.toString(),
  }).exec();

  // Throwing error if income not found
  if (!income) {
    res.status(404);
    next(new Error('Income not found'));
    return;
  }

  // Throwing error if income does not belongs to logged in user
  if (income.username !== req.username) {
    res.status(401);
    next(new Error('Unauthorized Access'));
    return;
  }

  // Saving income to the database
  income
    .remove()
    .then(() => {
      res.status(200);
      res.json({
        message: 'Income removed successfully',
        removedIncomeId: income._id,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = remove;
