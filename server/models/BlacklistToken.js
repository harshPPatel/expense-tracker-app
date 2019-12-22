const { Schema, model } = require('mongoose');

/**
 * Creates mongoose Scema for Blacklisted Tokens
 */
const blacklistTokenSchema = new Schema({
  token: {
    type: String,
    unique: true,
    trim: true,
    required: true,
  },
  expireAt: {
    type: Date,
    default: Date.now,
    index: { expires: '4d' },
  },
}, { timestamps: true });

module.exports = model('BlacklistToken', blacklistTokenSchema);
