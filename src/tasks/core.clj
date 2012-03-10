(ns tasks.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [tasks.tasks :as tasks]
            [tasks.home :as home]))

(defroutes app-routes
  (GET "/" [] (response/redirect "/tasks"))
  (GET "/github_oauth2_callback" {params :params session :session}
       (tasks/store-access-token-and-redirect-to-tasks params session))
  (GET "/tasks" {session :session}
       (home/gitwall session))
  (POST "/tasks" {params :params session :session}
        (tasks/create-task (params :task) session))
  (route/resources "/"))

(def app
  (handler/site app-routes))

