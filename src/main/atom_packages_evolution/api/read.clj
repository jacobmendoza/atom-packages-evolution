(ns atom-packages-evolution.api.read
  (:require
    [fulcro.server :refer [defquery-entity defquery-root]]
    [fulcro.i18n :as i18n]
    [fulcro-sql.core :as sql]
    [atom-packages-evolution.api.database :as db]
    [taoensso.timbre :as timbre]))

(defquery-root :packages-list/items
   (value [{:keys [databases]} params]
    (let [db (sql/get-dbspec databases :packages)]
      (db/get-packages db))))
