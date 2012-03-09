(ns tasks.core
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers)
  (:require [compojure.handler :as handler]))

(defn display []
  (html [:h1 "Hello world"]))

(defn add-new-task-form []
  (html
   (form-to [:post "/tasks/create"]
            "Task"
            [:br]
            (text-field :task)
            (submit-button "Add"))))

(defroutes myroutes
  (GET "/" [] (display))
  (GET "/tasks/new" [] (add-new-task-form)))

(def app
  (handler/site myroutes))