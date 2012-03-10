(ns tasks.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [tasks.login :as login]
            [tasks.tasks :as tasks]))

(defroutes app-routes
  (GET "/" [] (login/login-page))
  (GET "/tasks" {session :session}
       (tasks/add-new-task-form session))
  (POST "/tasks" {params :params session :session}
        (tasks/create-task (params :task) session))
  (route/resources "/"))

(def app
  (handler/site app-routes))

