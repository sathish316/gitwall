(ns tasks.github
  (use [clojure.data.json :only (read-json)])
  (:require [clj-http.client :as client]))

(def git-api-base-url "https://api.github.com/")
(def git-api-user-url (str git-api-base-url "user"))
(def access-token-param "?access_token=")

(defn get-data [url]
  (read-json (:body (client/get url))))

(defn user [access-token]
  (let [url (str git-api-user-url access-token-param access-token)
        user (get-data url)]
    user))

(defn user_login [access-token]
  (:login (user access-token)))

(defn repos [access-token]
  (if access-token
    (let [url (str git-api-user-url "/repos" access-token-param access-token)
          repos (get-data url)]
      repos)
    []))
  


