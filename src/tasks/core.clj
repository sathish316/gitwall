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
   (include-js "/javascripts/jquery.min.js")
   (include-js "/javascripts/application.js")
   (include-js "/javascripts/jquery.form.js")))
               
(defn add-new-task-form []
  (html
   (default-javascripts)
   (form-to {:id "new_task_form"} [:post "/tasks"]
            "Task"
            [:br]
            (text-field :task)
            (submit-button "Add"))
   [:div {:id "tasks_result"} "Tasks added so far"]))

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