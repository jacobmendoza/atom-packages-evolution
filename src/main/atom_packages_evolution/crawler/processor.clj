(ns atom-packages-evolution.crawler.processor
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [fulcro-sql.core :as sql]
            [clojure.walk :as walk]
            [taoensso.timbre :as timbre]
            [atom-packages-evolution.api.database :as dbapi]))

(def time-between-pages 3000)

(defn- log [message]
  (timbre/info "[Crawler]" message))

(defn- download-page [page]
  (->
    (client/get (str "https://atom.io/api/packages?page=" page) {:as :json})
    :body
    json/read-str
    walk/keywordize-keys))

(defn- package-exists? [db package]
  (some? (dbapi/get-id-by-package-name db (:name package))))

(defn- add-package [db package]
  (dbapi/insert-package db (:name package))
  (let [generated-id (dbapi/get-id-by-package-name db (:name package))]
    (dbapi/insert-package-statistics db generated-id (:downloads package) (:stargazers_count package))
    (dbapi/insert-package-version db generated-id (:latest (:releases package)))))

(defn- update-package [db package]
  (let [generated-id (dbapi/get-id-by-package-name db (:name package))]
    (dbapi/insert-package-statistics db generated-id (:downloads package) (:stargazers_count package))
    (dbapi/insert-package-version db generated-id (:latest (:releases package)))))

(defn process-with [databases]
  (let [db (sql/get-dbspec databases :packages)]
    (loop [i 1]
      (log (str "Downloading page " i))
      (let [contents (download-page i)]
        (when (not (empty? contents))
          (doseq [package contents]
            (log (:name package))
            (if (package-exists? db package)
              (update-package db package)
              (add-package db package)))
          (Thread/sleep time-between-pages)
          (recur (inc i)))))))
