(ns atom-packages-evolution.crawler.manager
  (:require [com.stuartsierra.component :as component]
            [atom-packages-evolution.crawler.processor :as processor]
            [taoensso.timbre :as timbre]))

(def do-process true)
(def time-between-crawling 3600000)

(defn- log [message]
  (timbre/info "[Crawler]" message))

(defn- build-thread-manager [databases]
  (let [continue (atom true)]
    {:start (fn []
              (doto
                (Thread.
                  (fn []
                    (log "Thread started")
                    (while @continue
                      (try
                        (do
                          (log "Reading packages information")
                          (if do-process
                            (processor/process-with databases)))
                        (catch Exception e
                          (timbre/info e)))
                      (Thread/sleep time-between-crawling))
                    (log "Thread finished")))
                (.start)))
     :stop (fn []
             (log "Thread requested to finish")
             (reset! continue false))}))

(defrecord CrawlerManager [databases thread-manager]
  component/Lifecycle

  (start [component]
    (log "Starting manager component")
    (let [thread-manager (build-thread-manager databases)]
      ((:start thread-manager))
      (assoc component :thread-manager thread-manager)))

  (stop [component]
    (log "Stopping manager component")
    ((:stop thread-manager))))
