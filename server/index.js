require('dotenv').config();
require('./db').getConnection();

const express = require('express');
const morgan = require('morgan');
const cors = require('cors');

const middlewares = require('./middlewares');
const validateToken = require('./validators/token');
const auth = require('./auth');
const user = require('./user');
const expense = require('./expense');
const income = require('./income');
const statement = require('./statements');

// Creating Express App
const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
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

// Users Routes
app.use('/api/v1/user', validateToken, user);

// Expense Routes
app.use('/api/v1/expense', validateToken, expense);

// Income Routes
app.use('/api/v1/income', validateToken, income);

// Statements Routes
app.use('/api/v1/statement', validateToken, statement);

// Error Handlers middlewares
app.use(middlewares.notFoundHandler); // 404
app.use(middlewares.errorHandler); // All errors

// Starting the app on `PORT`
app.listen(PORT, () => {
  console.log(`App has started at localhost:${PORT}`);
});
