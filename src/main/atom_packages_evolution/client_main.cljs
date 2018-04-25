(ns atom-packages-evolution.client-main
  (:require [atom-packages-evolution.client :as client]
            [fulcro.client :as core]
            [atom-packages-evolution.ui.root :as root]
            [atom-packages-evolution.ui.components :as comp]))

; This is the production entry point. In dev mode, we do not require this file at all, and instead mount (and
; hot code reload refresh) from cljs/user.cljs
(reset! client/app (core/mount @client/app comp/AtomPackagesList "app"))
