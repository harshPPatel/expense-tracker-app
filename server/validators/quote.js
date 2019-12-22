const joi = require('@hapi/joi');

/**
 * Validates the quote from request body
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const validateQuote = async (req, res, next) => {
  // Continuing the app if user chooses random quote
  if (req.body.isRandom) {
    next();
    return;
  }

  // Schema for quote
  const quoteSchema = joi.object({
    quote: joi.string()
      .trim()
      .min(5)
      .required(),
  });

  // validating request body for quote
  const validationResult = await quoteSchema.validate(req.body);

  // Throwing error if quote is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = validateQuote;
