const mongoose = require('mongoose');

const getQuote = () => {
  const quotes = [
    'Look everywhere you can to cut a little bit from your expenses. It will all add up to a meaningful sum.',
    'Control your expenses better than your competition. This is where you can always find the competitive advantage.',
    'Beware of little expenses. A small leak will sink a ship.',
    'Know the difference between your necessary and discretionary expenses.',
    'Clearly, any issues about breaching of expenses rules should be properly investigated.',
  ];

  return quotes[Math.round(Math.random() * quotes.length)];
};

const userSchema = new mongoose.Schema({
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
  message: {
    option: {
      type: Number,
      min: 0,
      max: 1,
      default: 0,
    },
    message: {
      type: String,
      minlength: 5,
      trim: true,
      default: getQuote(),
    },
  },
  status: {
    type: Number,
    min: 0,
    max: 1,
    required: true,
    default: 0,
  },
}, { timestamps: true });

module.exports = mongoose.model('User', userSchema);
