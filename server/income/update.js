/* eslint-disable no-underscore-dangle */
const User = require('../models/User');
const Income = require('../models/Income');

/**
 * Handles update request for income
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
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

  // Finding income in database
  const income = await Income.findOne({
    _id: req.body._id.toString(),
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

  // updating the income entry
  income.title = req.body.title.toString();
  income.amount = Number(req.body.amount.toString());
  income.date = new Date(req.body.date);

  // Saving income to the database
  income.save()
    .then(() => {
      res.status(200);
      res.json({
        message: 'Income updated successfully',
        updatedIncome: income,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = update;
