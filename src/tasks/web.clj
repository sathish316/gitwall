(ns tasks.web
  (:use compojure.core
        ring.adapter.jetty)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [tasks.tasks :as tasks]
            [tasks.home :as home]
            [tasks.mongo :as mongo]))

(defroutes app-routes
  (GET "/" [] (response/redirect "/tasks"))
  (GET "/github_oauth2_callback" {params :params session :session}
       (tasks/store-access-token-and-redirect-to-tasks params session))
  (GET "/tasks" {session :session}
       (home/gitwall session))
  (POST "/tasks" {params :params session :session}
        (tasks/create-task (params :task) session))
  (PUT "/tasks/:id" {params :params session :session}
       (tasks/update-task (Integer. (params :id))
                          (Integer. (params :status))
                          session))
  (route/resources "/"))

(def app
  (handler/site app-routes))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty app {:port port})))