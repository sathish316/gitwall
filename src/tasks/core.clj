(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers)
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

(defn create-task [task]
  {:body (str "Saved " task)
   :session {:tasks task}})

(defn view-tasks [tasks]
  (html
   [:h1 (str "Tasks")]
   [:p tasks]))

(defroutes myroutes
  (GET "/" [] (display))
  (GET "/tasks/new" [] (add-new-task-form))
  (POST "/tasks" {params :params}
        (create-task (params :task)))
  (GET "/tasks" {session :session}
       (view-tasks (:tasks session))))
        

(def app
  (handler/site myroutes))