const { Schema, model } = require('mongoose');

/**
 * Creates mongoose Scema for Expense Entries
 */
const incomeSchema = new Schema({
  title: {
    type: String,
    trim: true,
    required: true,
  },
  amount: {
    type: Number,
    min: 0,
    required: true,
  },
  date: {
    type: Date,
    default: Date.now(),
    required: true,
  },
  username: {
    type: String,
    minlength: 3,
    maxlength: 15,
    trim: true,
    required: true,
    match: /(^[a-zA-Z0-9_]+$)/,
  },
}, { timestamps: true });

module.exports = model('Income', incomeSchema);
