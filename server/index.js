require('dotenv').config();

const express = require('express');
const morgan = require('morgan');

const middlewares = require('./middlewares');

// Creating Express App
const app = express();
const PORT = process.env.PORT || 5000;

app.use(morgan('dev'));

// Root Route
app.get('/', (req, res) => {
  res.json({
    message: 'Welcome to the Expense Tracker App\'s API!',
  });
});

// Error Handlers middlewares
app.use(middlewares.notFoundHandler); // 404
app.use(middlewares.errorHandler); // All errors

// Starting the app on `PORT`
app.listen(PORT, () => {
  console.log(`App has started at localhost:${PORT}`);
});
