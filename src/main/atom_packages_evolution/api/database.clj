(ns atom-packages-evolution.api.database
  (:require [fulcro-sql.core :as sql]
            [clojure.java.jdbc :as jdbc]
            [clojure.set :as set]))

(def schema
  {::sql/joins      {}
   ::sql/driver     :mysql
   ::sql/graph->sql {}
   ::sql/pks        {}})

(def query "SELECT p.id, p.package_name, s.downloads, s.stargazers_count FROM packages p
            INNER JOIN package_statistics s ON p.id = s.package_id
            WHERE s.id = (SELECT id FROM package_statistics ps WHERE ps.package_id = p.id ORDER BY ID DESC LIMIT 1)
            ORDER BY p.id")

(defn get-packages [db]
  (let [rows (jdbc/query db [query])]
    (mapv #(set/rename-keys % {:id :db/id
                               :package_name :package/name
                               :downloads :package/downloads
                               :stargazers_count :package/stargazers}) rows)))

(defn get-id-by-package-name [db package-name]
  (:id (first (jdbc/query db ["SELECT id FROM packages WHERE package_name = ?" package-name]))))

(defn insert-package [db package-name]
  (jdbc/execute! db ["INSERT INTO packages (package_name) VALUES (?)" package-name]))

(defn insert-package-statistics [db package-id downloads stargazers]
  (jdbc/execute! db ["INSERT INTO package_statistics (package_id, date_time, downloads, stargazers_count) VALUES (?, NOW(), ?, ?)" package-id downloads stargazers]))

(defn insert-package-version [db package-id version]
  (jdbc/execute! db ["INSERT INTO package_versions (package_id, date_time, version) VALUES (?, NOW(), ?)" package-id version]))
