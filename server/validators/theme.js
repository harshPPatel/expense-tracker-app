const joi = require('@hapi/joi');

const validateTheme = async (req, res, next) => {
  // Schema for quote
  const quoteSchema = joi.object({
    theme: joi.number()
      .min(0)
      .max(3)
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

module.exports = validateTheme;
