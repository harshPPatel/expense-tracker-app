const joi = require('@hapi/joi');
joi.objectId = require('joi-objectid')(joi);

// Common Expense Schema for validation
const expenseSchema = {
  title: joi.string()
    .trim()
    .min(3)
    .required(),
  amount: joi.number()
    .min(0)
    .required(),
  date: joi.date()
    .required(),
};

/**
 * Validates expense object from request object for create request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const create = async (req, res, next) => {
  // Schema for expense
  const createExpenseSchema = joi.object(expenseSchema);

  // validating request body for expense
  const validationResult = await createExpenseSchema.validate(req.body);

  // Throwing error if expense is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

/**
 * Validates expense object from request object for update request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const update = async (req, res, next) => {
  // Schema for expense
  const updateExpenseSchema = joi.object({
    ...expenseSchema,
    _id: joi.objectId()
      .required(),
  });

  // validating request body for expense
  const validationResult = await updateExpenseSchema.validate(req.body);

  // Throwing error if expense is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

/**
 * Validates expense object from request object for remove request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const remove = async (req, res, next) => {
  // Schema for expense
  const removeExpenseSchema = joi.object({
    _id: joi.objectId()
      .required(),
  });

  // validating request body for expense
  const validationResult = await removeExpenseSchema.validate(req.body);

  // Throwing error if expense is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = {
  create,
  update,
  remove,
};
