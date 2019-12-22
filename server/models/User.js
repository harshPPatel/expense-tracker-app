const { Schema, model } = require('mongoose');

const quote = require('../utils/quote');

/**
 * Creates mongoose Scema for User
 */
const userSchema = new Schema({
  username: {
    type: String,
    minlength: 3,
    maxlength: 15,
    unique: true,
    trim: true,
    required: true,
    match: /(^[a-zA-Z0-9_]+$)/,
  },
  password: {
    type: String,
    required: true,
  },
  quote: {
    type: String,
    minlength: 5,
    trim: true,
    default: quote.getRandomQuote(),
  },
  theme: {
    type: Number,
    min: 0,
    max: 3,
    required: true,
    default: 0,
  },
  currency: {
    type: String,
    minLength: 1,
    maxlength: 1,
    required: true,
    default: '$',
    match: /^(\$|€|₹|£|¥)$/,
  },
  expenseWarningLimit: {
    type: Number,
    min: 0,
    required: true,
    default: 1000,
  },
}, { timestamps: true });

module.exports = model('User', userSchema);
