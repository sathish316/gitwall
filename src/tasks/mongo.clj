(ns tasks.mongo
  (:use somnium.congomongo))

(defn make-connection-for-env [env]
  (condp = env
    :dev
    (make-connection "gitwall"
                     :host "127.0.0.1"
                     :port 27017)
    :prod
    (make-connection "gitwall"
                     :host "127.0.0.1"
                     :port 27017)))

(def conn (make-connection-for-env :dev))

(defn connect! []
  (set-connection! conn))

(connect!)