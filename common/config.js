  const config  = {
    db: {
      production: 'mongodb://127.0.0.1/atom-packages-evolution',
      test: 'mongodb://127.0.0.1/atom-packages-evolution-test'
    },
    timeBetweenCrawl: 5000
  };

  module.exports = config;
