import mongoose from 'mongoose';

const PackageModel = mongoose.model('AtomPackage', {
  name: String,
  updates: [{
    downloads: Number,
    stargazers_count: Number,
    date_time: Date
  }]
});

module.exports = PackageModel;
