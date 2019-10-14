require('dotenv').config();
require('./db');

const express = require('express');
const morgan = require('morgan');
const cors = require('cors');

const middlewares = require('./middlewares');

const app = express();

app.use(cors());
app.use(morgan('dev'));
app.use(express.json());

app.get('/api/v1/', (req, res) => {
  res.json({
    message: 'Hello World !😀',
  });
});

// Handling Not Found Case
app.use(middlewares.notFound);

// Error Handler for API
app.use(middlewares.errorHandler);

module.exports = app;
