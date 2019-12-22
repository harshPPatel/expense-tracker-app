const monk = require('monk');

// Database Connection URI
const URI = process.env.MONGO_URI
  ? process.env.MONGO_URI
  : 'localhost/expense-tracker-app';

export default monk(URI);
