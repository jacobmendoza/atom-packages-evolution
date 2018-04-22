(ns atom-packages-evolution.api.read
  (:require
    [fulcro.server :refer [defquery-entity defquery-root]]
    [fulcro.i18n :as i18n]
    [taoensso.timbre :as timbre]))

(defquery-root :packages-list/items
  (value [env params]
    [{:db/id 1 :package/name "Package 1" :package/downloads 999 :package/stargazers 777}
     {:db/id 2 :package/name "Package 2" :package/downloads 888 :package/stargazers 555}]))
