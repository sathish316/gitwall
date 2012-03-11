(ns tasks.mongo
  (:use somnium.congomongo))

(defn mongo-params [mongo-uri]
  (let [[all user password host port db] (re-find #"mongodb://(\w+):(\w+)@(.*?):(\w+)\/(\w+)" mongo-uri)]
    {:user user :password password :host host :port (Integer. port) :db db}))

(defn connect! []
  (if (System/getenv "MONGOLAB_URI")
    (let [params (mongo-params (System/getenv "MONGOLAB_URI"))
          connection (make-connection (:db params)
                                      :host (:host params)
                                      :port (:port params))]
      (set-connection! connection)
      (authenticate connection
                    (:user params)
                    (:password params)))
    (let [connection (make-connection "gitwall"
                                      :host "127.0.0.1"
                                      :port 27017)]
      (set-connection! connection))))

(connect!)