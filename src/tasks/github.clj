(ns tasks.github
  (use [clojure.data.json :only (read-json)])
  (:require [clj-http.client :as client]))

(def git-api-base-url "https://api.github.com/")
(def git-api-user "user")
(def access-token-param "?access_token=")

(defn user [access-token]
  (let [url (str git-api-base-url git-api-user access-token-param access-token)
        user (read-json (:body (client/get url)))]
    user))

(defn user_login [access-token]
  (:login (user access-token)))


