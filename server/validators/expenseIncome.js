const joi = require('@hapi/joi');
joi.objectId = require('joi-objectid')(joi);

// Common Expense Schema for validation
const expenseIncomeSchema = {
  title: joi.string()
    .trim()
    .min(3)
    .required(),
  amount: joi.number()
    .precision(2)
    .min(0)
    .required(),
  date: joi.date()
    .required(),
};

/**
 * Validates expense OR income object from request object for create request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const create = async (req, res, next) => {
  // Schema for expense
  const createExpenseIncomeSchema = joi.object(expenseIncomeSchema);

  // validating request body for expense
  const validationResult = await createExpenseIncomeSchema.validate(req.body);

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
 * Validates expense OR income object from request object for update request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const update = async (req, res, next) => {
  // Schema for expense
  const updateExpenseIncomeSchema = joi.object({
    ...expenseIncomeSchema,
    _id: joi.objectId()
      .required(),
  });

  // validating request body for expense
  const validationResult = await updateExpenseIncomeSchema.validate(req.body);

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
  const removeExpenseIncomeSchema = joi.object({
    _id: joi.objectId()
      .required(),
  });

  // validating request body for expense
  const validationResult = await removeExpenseIncomeSchema.validate(req.body);

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
