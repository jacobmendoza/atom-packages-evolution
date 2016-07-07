import express from 'express';
import mongoose from 'mongoose';
import cons from 'consolidate';
import config from './config';
import PackageModel from './package-model';

mongoose.connect(config.db.production);

const app = express();
app.engine('mustache', cons.mustache);
app.set('views', './views');
app.set('view engine', 'mustache');
app.use(express.static('public'));

app.get('/', async (req, res) => {
	const atomPackagesList = await PackageModel.find({});
	const models = atomPackagesList.map(atomPackage => {
		return {
			name: atomPackage.name,
			downloads: atomPackage.updates[atomPackage.updates.length - 1].downloads
		};
	});
	res.render('index', {atomPackagesList: models});
});

app.get('/package/:packageName', async (req, res) => {
	const atomPackage = await PackageModel.findOne({name: req.params.packageName});

	if (!atomPackage) {
		res.status(404).send('Not found');
		return;
	}

	const lastVersion = atomPackage.updates[atomPackage.updates.length - 1];
	const model = {
		name: atomPackage.name,
		downloads: lastVersion.downloads,
		stargazersCount: lastVersion.stargazers_count
	};
	res.render('detail', model);
});

app.listen(3000, () => {
	console.log('Example app listening on port 3000!');
});
