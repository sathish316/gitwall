(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn display []
  (html [:h1 "Hello world"]))

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

(defn add-new-task-form []
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
     [:div {:id "tasks_result"} "Tasks added so far"]]]))
  
(defn create-task [task session]
  (let [tasks (session :tasks)
        result (if (vector? tasks)
                 (conj tasks task)
                 (vector task))]
    {:body (html (unordered-list result))
     :session {:tasks result}}))

(defn view-tasks [tasks session]
  (html
   [:h1 "Tasks"]
   (unordered-list (:tasks session))))

(defroutes myroutes
  (GET "/" [] (display))
  (GET "/tasks/new" [] (add-new-task-form))
  (POST "/tasks" {params :params session :session}
        (create-task (params :task) session))
  (GET "/tasks" {session :session}
       (view-tasks (:tasks session) session))
  (route/resources "/"))

(def app
  (handler/site myroutes))