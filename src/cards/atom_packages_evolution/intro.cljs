(ns atom-packages-evolution.intro
  (:require [devcards.core :as rc :refer-macros [defcard]]
            [atom-packages-evolution.ui.components :as comp]))

(defcard SVGPlaceholder
  "# SVG Placeholder"
  (comp/ui-placeholder {:w 200 :h 200}))
