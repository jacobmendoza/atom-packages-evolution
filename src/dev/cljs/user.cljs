(ns cljs.user
  (:require
    [fulcro.client :as fc]
    [atom-packages-evolution.client :as core]
    [atom-packages-evolution.ui.root :as root]
    [cljs.pprint :refer [pprint]]
    [fulcro.client.logging :as log]
    [atom-packages-evolution.ui.components :as comp]))

(enable-console-print!)

(log/set-level :all)

(defn mount []
  (reset! core/app (fc/mount @core/app comp/AtomPackagesList "app")))

(mount)
