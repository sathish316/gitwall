(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [compojure.handler :as handler]))

(defn display []
  (html [:h1 "Hello world"]))

(defn add-new-task-form []
  (html
   (form-to [:post "/tasks"]
            "Task"
            [:br]
            (text-field :task)
            (submit-button "Add"))))

(defn create-task [task session]
  (let [tasks (session :tasks)]
    {:body (str "Saved " task)
     :session {:tasks (if (vector? tasks)
                        (conj tasks task)
                        (vector task))}}))

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
       (view-tasks (:tasks session) session)))
        

(def app
  (handler/site myroutes))