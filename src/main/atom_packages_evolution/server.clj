(ns atom-packages-evolution.server
  (:require
    [fulcro.easy-server :refer [make-fulcro-server]]
    ; MUST require these, or you won't get them installed.
    [atom-packages-evolution.api.read]
    [atom-packages-evolution.api.mutations]))

(defn build-server
  [{:keys [config] :or {config "config/dev.edn"}}]
  (make-fulcro-server
    :parser-injections #{:config}
    :config-path config))



