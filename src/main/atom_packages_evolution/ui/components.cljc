(ns atom-packages-evolution.ui.components
  (:require
    [fulcro.client.primitives :as prim :refer [defsc]]
    #?(:cljs [fulcro.client.dom :as dom] :clj [fulcro.client.dom-server :as dom])))

(defn- format-ratio [ratio]
  (let [p (* (- ratio 1) 100)]
    (if (>= ratio 1)
      (str " (+" p "%)")
      (str " (-" p "%)"))))

(defsc AtomPackage [this {:keys [db/id package/name package/downloads package/stargazers package/increase-ratio package/update-time] :as props}]
  {:query [:db/id :package/name :package/downloads :package/stargazers :package/increase-ratio :package/update-time]
   :ident [:atom-packages/by-id :db/id]}
  (dom/div #js {:className "atom-package"}
    (dom/div #js {:className "title-container"}
      (dom/div #js {:className "title"} name)
      (dom/div #js {:className "update"} (str update-time)))
    (dom/div #js {:className "statistics-container"}
      (dom/div #js {:className "downloads"} downloads
        (dom/span #js {:className "ratio"} (format-ratio increase-ratio)))
      (dom/div #js {} stargazers))))


(def ui-atom-package (prim/factory AtomPackage {:keyfn :db/id}))

(defsc AtomPackagesList [this {:keys [packages-list/items] :as props}]
  {:query [{:packages-list/items (prim/get-query AtomPackage)}]}
  (dom/div #js {}
    (dom/h1 #js {} "List of packages")
    (mapv ui-atom-package items)))

(def ui-atom-packages-list (prim/factory AtomPackagesList))
