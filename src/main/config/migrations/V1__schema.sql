DROP TABLE IF EXISTS packages;
DROP TABLE IF EXISTS package_statistics;
DROP TABLE IF EXISTS package_versions;

CREATE TABLE packages (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  package_name TEXT NOT NULL
);

CREATE TABLE package_statistics (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  package_id INT UNSIGNED,
  date_time DATETIME,
  downloads INT UNSIGNED,
  stargazers_count INT UNSIGNED,
  FOREIGN KEY (package_id) REFERENCES packages(id)
);

CREATE TABLE package_versions (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  package_id INT UNSIGNED,
  date_time DATETIME,
  version TEXT,
  FOREIGN KEY (package_id) REFERENCES packages(id)
);

INSERT INTO packages (package_name) VALUES ('abcd');
INSERT INTO packages (package_name) VALUES ('edcx');
INSERT INTO package_statistics(package_id, date_time, downloads, stargazers_count) VALUES (1, NOW(), 1, 2);
INSERT INTO package_statistics(package_id, date_time, downloads, stargazers_count) VALUES (2, NOW(), 3, 4);
INSERT INTO package_versions(package_id, date_time, version) VALUES (1, NOW(), '2.3.0');
INSERT INTO package_versions(package_id, date_time, version) VALUES (2, NOW(), '2.3.0');
