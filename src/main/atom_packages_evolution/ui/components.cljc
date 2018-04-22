(ns atom-packages-evolution.ui.components
  (:require
    [fulcro.client.primitives :as prim :refer [defsc]]
    #?(:cljs [fulcro.client.dom :as dom] :clj [fulcro.client.dom-server :as dom])))

(defsc AtomPackage [this {:keys [db/id package/name package/downloads package/stargazers] :as props}]
  {:query [:db/id :package/name :package/downloads :package/stargazers]
   :ident [:atom-packages/by-id :db/id]}
  (dom/div #js {:key (str id)}
    (dom/div #js {} name)
    (dom/div #js {} downloads)
    (dom/div #js {} stargazers)
    (dom/hr #js {})))

(def ui-atom-package (prim/factory AtomPackage))

(defsc AtomPackagesList [this {:keys [packages-list/items] :as props}]
  {:query [{:packages-list/items (prim/get-query AtomPackage)}]}
  (dom/div #js {}
    (dom/h1 #js {} "List of packages")
    (mapv ui-atom-package items)))

(def ui-atom-packages-list (prim/factory AtomPackagesList))
