require('dotenv').config();
require('./db').getConnection();

const express = require('express');
const morgan = require('morgan');

const middlewares = require('./middlewares');
const auth = require('./auth');

// Creating Express App
const app = express();
const PORT = process.env.PORT || 5000;

app.use(express.json());
app.use(morgan('dev'));

// Root Route
app.get('/', (req, res) => {
  res.json({
    message: 'Welcome to the Expense Tracker App\'s API!',
  });
});

// Authenticatino Routes
app.use('/api/v1/auth', auth);

// Error Handlers middlewares
app.use(middlewares.notFoundHandler); // 404
app.use(middlewares.errorHandler); // All errors

// Starting the app on `PORT`
app.listen(PORT, () => {
  console.log(`App has started at localhost:${PORT}`);
});
