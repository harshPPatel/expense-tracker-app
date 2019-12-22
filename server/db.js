const db = require('mongoose');

// Database connection URI
const URI = process.env.MONGO_URI || 'mongodb://localhost/expense-tracker-app';

// Creates the connection
const getConnection = () => {
  db.connect(URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
    .then(() => {
      console.log('Database Connected');
    })
    .catch((err) => {
      console.error(err);
    });
  return db;
};

module.exports = {
  getConnection,
};
