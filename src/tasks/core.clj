(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn default-javascripts []
  (html
   (include-js "/js/jquery.min.js")
   (include-js "/js/application.js")
   (include-js "/js/jquery.form.js")
   (include-js "/js/bootstrap.js")))

(defn default-stylesheets []
  (html
   (include-css "/css/bootstrap.min.css")
   (include-css "/css/bootstrap-responsive.min.css")))

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

(defroutes myroutes
  (GET "/tasks" {session :session}
       (add-new-task-form session))
  (POST "/tasks" {params :params session :session}
        (create-task (params :task) session))
  (route/resources "/"))

(def app
  (handler/site myroutes))