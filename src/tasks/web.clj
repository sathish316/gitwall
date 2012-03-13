(ns tasks.web
  (:use compojure.core
        ring.adapter.jetty)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [tasks.tasks :as tasks]
            [tasks.home :as home]
            [tasks.mongo :as mongo]
            [ring.util.response :as response]
            [tasks.oauth_github :as oauth_github]))

(defroutes app-routes
  (GET "/" [] (response/redirect "/tasks"))
  (GET "/login" []
       (response/redirect (:uri oauth_github/auth-req)))
  (GET "/logout" {session :session}
       (-> (response/redirect "/tasks")
           (assoc :session {})))
  (GET "/github_oauth2_callback" {params :params session :session}
       (tasks/store-access-token-and-redirect-to-tasks params session))
  (GET "/tasks" {params :params session :session}
       (home/gitwall (params :project)
                     session))
  (POST "/tasks" {params :params session :session}
        (tasks/create-task (params :project)
                           (params :task)
                           session))
  (PUT "/tasks/:id" {params :params session :session}
       (tasks/update-task (params :project)
                          (Integer. (params :id))
                          (Integer. (params :status))
                          session))
  (DELETE "/tasks/:id" {params :params session :session}
          (tasks/delete-task (params :project)
                             (Integer. (params :id))
                             session))
  (route/resources "/"))

(def app
  (handler/site app-routes))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty app {:port port})))