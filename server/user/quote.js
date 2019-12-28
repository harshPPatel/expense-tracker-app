const { Router } = require('express');

const User = require('../models/User');
const validateQuote = require('../validators/quote');
const { getRandomQuote } = require('../utils/quote');

const router = Router();

/**
 * Handles the get request of user's quote
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

  // Returning the quote to the user
  res.status(200);
  res.json({
    username: user.username,
    quote: user.quote,
  });
});

/**
 * Handles the update(put) request of user's quote
 */
router.put('/update', validateQuote, async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Updating the quote
  user.quote = req.body.isRandom ? getRandomQuote() : req.body.quote.toString();

  // Saving user in database
  user.updateOne()
    .then(() => {
      // responsing back to the user
      res.status(200);
      res.json({
        username: req.username,
        message: 'Quote has updated successfully!',
        updatedQuote: user.quote,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
});

module.exports = router;
