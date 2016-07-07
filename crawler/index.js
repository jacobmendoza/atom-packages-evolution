import got from 'got';
import mongoose from 'mongoose';
import SavePackageInfo from './save-package-info';
import config from './config';
import {CronJob} from 'cron';

mongoose.connect(config.db.production);

function getJsonFromPage(url) {
  const options = { json: true };
  return new Promise((resolve, reject) => {
    got(url, options)
        .then(response => resolve(response.body))
        .catch(error => reject(error.response.body));
  });
}

function setTimeoutPromise() {
  return new Promise((resolve) => {
    setTimeout(resolve, config.timeBetweenCrawl);
  });
}

async function doProcess() {
  let currentPage = 1;
  let shouldContinue = true;

  while (shouldContinue) {
    let currentUrl = `https://atom.io/api/packages?page=${currentPage}`;
    console.log(`Downloading ${currentUrl}`);
    var atomPackagesInfo = await getJsonFromPage(currentUrl);

    atomPackagesInfo.forEach(atomPackage => {
      const handler = new SavePackageInfo();
      handler.execute({
        name: atomPackage.name,
        downloads: atomPackage.downloads,
        stargazers_count: atomPackage.stargazers_count
      });
    });

    currentPage++;
    await setTimeoutPromise();
  }
}

new CronJob('0 */2 * * *', doProcess, null, true, 'America/Los_Angeles');

doProcess();
