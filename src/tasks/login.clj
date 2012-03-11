(ns tasks.login
  (:use compojure.core))

(defn github-or-anonymous-user [session]
  (or (session :github_username)
      (session :anonymous_username)
      (let [anonymous-username (str "anonymous-"
                                    (.nextInt (java.util.Random.) 100)
                                    "-"
                                    (.getTime (java.util.Date.)))]
        anonymous-username)))

(defn github-or-anonymous-user-type [session]
  (cond
   (session :github_username) :github_username
   :else :anonymous_username))
