(ns atom-packages-evolution.client-test-main
  (:require atom-packages-evolution.tests-to-run
            [fulcro-spec.selectors :as sel]
            [fulcro-spec.suite :as suite]))

(enable-console-print!)

(suite/def-test-suite client-tests {:ns-regex #"atom-packages-evolution..*-spec"}
  {:default   #{::sel/none :focused}
   :available #{:focused}})

