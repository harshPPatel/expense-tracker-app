const User = require('../models/User');
const Income = require('../models/Income');

/**
 * Handles create request for income
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
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

  // creating the income entry
  const income = new Income({
    title: req.body.title.toString(),
    amount: Number(req.body.amount.toString()),
    date: new Date(req.body.date),
    username: req.username,
  });

  // Saving income to the database
  income.save()
    .then((response) => {
      res.status(200);
      res.json({
        message: 'Income created successfully',
        income: response,
        username: req.username,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = create;
