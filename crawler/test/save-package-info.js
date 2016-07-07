import test from 'ava';
import "babel-polyfill";
import sinon from 'sinon';
import mongoose from 'mongoose';
import {SavePackageInfo, PackageSchema} from '../save-package-info';

mongoose.connect(config.db.test);

let sut;

test.cb.beforeEach('setup', t => {
	sut = new SavePackageInfo();
	mongoose.connection.db.dropCollection('atompackages', err => {
		t.end();
	});
});

test('if record does not exist gets inserted', async t => {
	await sut.execute({ name: 'insert' });

	const atomPackage = await PackageSchema.findOne({ name: 'insert' });

	t.is(atomPackage.name, 'insert');
});

test('if record does not exist gets updated', async t => {
	const atomPackage = new PackageSchema({
		name: 'update',
		updates: [{}]
	});

	await atomPackage.save();

	await sut.execute({ name: 'update' });

	const atomPackageFromDb = await PackageSchema.findOne({ name: 'update' });

	t.is(atomPackageFromDb.name, 'update');
	t.is(atomPackageFromDb.updates.length, 2);
});
