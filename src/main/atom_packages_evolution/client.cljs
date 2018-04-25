(ns atom-packages-evolution.client
  (:require [fulcro.client :as fc]
            [fulcro.client.data-fetch :as df]
            [fulcro.i18n :as i18n]
            [atom-packages-evolution.ui.components :as comp]))

(defn message-format [{:keys [::i18n/localized-format-string ::i18n/locale ::i18n/format-options]}]
  (let [locale-str (name locale)
        ; comes from js file included in HTML. Use shadow-cljs instead of figwheel to make this cleaner
        formatter  (js/IntlMessageFormat. localized-format-string locale-str)]
    (.format formatter (clj->js format-options))))

(defonce app (atom (fc/new-fulcro-client
                    :started-callback
                     (fn [app]
                       (df/load app :packages-list/items comp/AtomPackage))
                     :reconciler-options {:shared    {::i18n/message-formatter message-format}
                                          :shared-fn ::i18n/current-locale})))
