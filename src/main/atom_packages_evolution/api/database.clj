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

(defn insert-package [db package-name]
  (jdbc/execute! db ["INSERT INTO Packages (package_name) VALUES (?)" package-name]))
