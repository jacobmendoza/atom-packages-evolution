(ns atom-packages-evolution.api.database
  (:require [fulcro-sql.core :as sql]
            [clojure.java.jdbc :as jdbc]
            [clojure.set :as set]))

(def schema
  {::sql/joins      {}
   ::sql/driver     :mysql
   ::sql/graph->sql {}
   ::sql/pks        {}})

(def query "SELECT p.id, p.package_name, s.downloads, s.stargazers_count, s.date_time,
            (SELECT ps.downloads FROM package_statistics ps WHERE ps.package_id = p.id ORDER BY ps.id DESC LIMIT 1, 1) AS prev_downloads,
          		(SELECT ps.downloads FROM package_statistics ps WHERE ps.package_id = p.id ORDER BY ps.id DESC LIMIT 1) /
          		(SELECT ps.downloads FROM package_statistics ps WHERE ps.package_id = p.id ORDER BY ps.id DESC LIMIT 1, 1) AS ratio
            FROM packages p INNER JOIN package_statistics s ON p.id = s.package_id
            WHERE s.id = (SELECT id FROM package_statistics ps WHERE ps.package_id = p.id ORDER BY ID DESC LIMIT 1)
            HAVING (SELECT COUNT(*) FROM package_statistics pss WHERE pss.package_id = p.id) > 1
            ORDER BY ratio DESC LIMIT 200")

(defn get-packages [db]
  (let [rows (jdbc/query db [query])]
    (mapv #(set/rename-keys % {:id :db/id
                               :package_name :package/name
                               :downloads :package/downloads
                               :prev_downloads :package/previous-downloads
                               :stargazers_count :package/stargazers
                               :date_time :package/update-time
                               :ratio :package/increase-ratio})
       rows)))

(defn get-id-by-package-name [db package-name]
  (:id (first (jdbc/query db ["SELECT id FROM packages WHERE package_name = ?" package-name]))))

(defn insert-package [db package-name]
  (jdbc/execute! db ["INSERT INTO packages (package_name) VALUES (?)" package-name]))

(defn insert-package-statistics [db package-id downloads stargazers]
  (jdbc/execute! db ["INSERT INTO package_statistics (package_id, date_time, downloads, stargazers_count) VALUES (?, NOW(), ?, ?)" package-id downloads stargazers]))

(defn insert-package-version [db package-id version]
  (jdbc/execute! db ["INSERT INTO package_versions (package_id, date_time, version) VALUES (?, NOW(), ?)" package-id version]))
