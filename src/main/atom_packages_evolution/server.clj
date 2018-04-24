(ns atom-packages-evolution.server
  (:require
    [fulcro.easy-server :refer [make-fulcro-server]]
    ; MUST require these, or you won't get them installed.
    [atom-packages-evolution.api.read]
    [atom-packages-evolution.api.mutations]
    [fulcro-sql.core :as sql]
    [com.stuartsierra.component :as component]))

(defn build-server
  [{:keys [config] :or {config "config/dev.edn"}}]
  (make-fulcro-server
    :components {:databases (component/using (sql/build-db-manager {}) [:config])}
    :parser-injections #{:databases :config}
    :config-path config))
