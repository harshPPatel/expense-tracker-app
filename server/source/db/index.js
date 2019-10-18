/* eslint-disable no-console */
const db = require('mongoose');

db.connect(process.env.MONGODB_URI, { 
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => {
    console.log('Database Connected');
  })
  .catch((err) => {
    console.error(err);
  });

module.exports = db;
