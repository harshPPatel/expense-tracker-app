const joi = require('@hapi/joi');

/**
 * Validates theme from request object
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const validateTheme = async (req, res, next) => {
  // Schema for theme
  const themeSchema = joi.object({
    theme: joi.number()
      .min(0)
      .max(3)
      .required(),
  });

  // validating request body for theme
  const validationResult = await themeSchema.validate(req.body);

  // Throwing error if theme is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = validateTheme;
