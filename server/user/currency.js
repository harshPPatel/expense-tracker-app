const { Router } = require('express');

const validateCurrency = require('../validators/currency');
const User = require('../models/User');

const router = Router();

/**
 * Handles the get request for currency of the user
 */
router.get('/', async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Returning the currency to the user
  res.status(200);
  res.json({
    username: user.username,
    currency: user.currency,
  });
});

/**
 * Handles the update request for currency of users
 */
router.put('/update', validateCurrency, async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Updating the currency
  user.currency = req.body.currency.toString();

  // Saving user in database
  user
    .save()
    .then(() => {
      // responsing back to the user
      res.status(200);
      res.json({
        username: req.username,
        message: 'Currency has updated successfully!',
        updatedCurrency: user.currency,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
});

module.exports = router;
