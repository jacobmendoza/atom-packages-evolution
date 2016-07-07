import PackageModel from './package-model';

class SavePackageInfo {
  execute(command) {
    return new Promise((resolve, reject) => {
      PackageModel.findOne({ 'name': command.name }, function (err, atomPackage) {
        if (err) return reject(err);

        if (!atomPackage) {
          atomPackage = new PackageModel({
            name: command.name,
            updates: []
          });
        }

        atomPackage.updates.push({
          date_time: new Date(),
          downloads: command.downloads,
          stargazers_count: command.stargazers_count
        });

        atomPackage.save(function (err) {
          if (err) { reject(err); }
          else { resolve(); }
        });
      });
    });
  }
}

module.exports = SavePackageInfo;
