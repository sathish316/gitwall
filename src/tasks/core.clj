(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [tasks.oauth_github :as oauth_github]
            tasks.assets))

;;TODO: Move to login.clj
(defn login-page []
  (html
   [:html
    [:head
     (default-stylesheets)]
    [:body
     [:div
      [:h2 "Github Tasks"]
      (form-to [:post (:uri oauth_github/auth-req)]
               (submit-button "Sign in using Github"))]]]))

;;TODO: Move to tasks.clj
(defn add-new-task-form [session]
  (html
   [:html
    [:head
     (default-javascripts)
     (default-stylesheets)]
    [:body
     (form-to {:id "new_task_form"} [:post "/tasks"]
              "Task"
              [:br]
              (text-field :task)
              (submit-button {:class "btn-primary"} "Add"))
     [:div {:id "tasks"}
      (unordered-list (:tasks session))]]]))
  
(defn create-task [task session]
  (let [tasks (session :tasks)
        result (if (vector? tasks)
                 (conj tasks task)
                 (vector task))]
    {:body (html (unordered-list result))
     :session {:tasks result}}))

;;TODO: Move to routes.clj
(defroutes app-routes
  (GET "/" [] (login-page))
;  (GET "/" [] (response/redirect (:uri oauth_github/auth-req)))
  (GET "/tasks" {session :session}
       (add-new-task-form session))
  (POST "/tasks" {params :params session :session}
        (create-task (params :task) session))
  (route/resources "/"))

(def app
  (handler/site app-routes))

