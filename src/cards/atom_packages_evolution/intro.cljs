(ns atom-packages-evolution.intro
  (:require [devcards.core :as rc :refer-macros [defcard]]
            [atom-packages-evolution.ui.components :as comp]))

(defcard AtomPackage
  "# Atom Package"
  (comp/ui-atom-package {:db/id 1 :package/name "rspec-tree-runner" :package/downloads 55 :package/stargazers 123}))

(defcard AtomPackagesList
  "# Atom Packages List"
  (comp/ui-atom-packages-list
   {:packages-list/items [{:db/id 1 :package/name "a" :package/downloads 1 :package/stargazers 2}
                          {:db/id 2 :package/name "b" :package/downloads 3 :package/stargazers 4}
                          {:db/id 3 :package/name "c" :package/downloads 5 :package/stargazers 6}]}))
