const app = require('./app');

const PORT = process.env.PORT || 4000;

app.listen(PORT, () => {
  /* eslint-disable no-console */
  console.log(`Server started at http://localhost:${PORT}`);
  /* eslint-enable no-console */
});
