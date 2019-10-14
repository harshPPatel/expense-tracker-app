require('dotenv').config();

const express = require('express');
const morgan = require('morgan');
const cors = require('cors');

const app = express();

app.use(cors());
app.use(morgan('dev'));
app.use(express.json());

app.get('/api/v1/', (req, res) => {
  res.json({
    message: 'Hello World !😀',
  });
});

module.exports = app;
