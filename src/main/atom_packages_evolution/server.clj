(ns atom-packages-evolution.server
  (:require
    [fulcro.easy-server :refer [make-fulcro-server]]
    [fulcro-sql.core :as sql]
    [com.stuartsierra.component :as component]
    [atom-packages-evolution.crawler.manager :as crawler]
    ; Require for reads and mutations
    [atom-packages-evolution.api.read]
    [atom-packages-evolution.api.mutations]))

(defn build-server
  [{:keys [config] :or {config "config/dev.edn"}}]
  (make-fulcro-server
    :components {:databases (component/using (sql/build-db-manager {}) [:config])
                 :crawler-manager (component/using (crawler/map->CrawlerManager {}) [:databases])}
    :parser-injections #{:databases :config}
    :config-path config))
