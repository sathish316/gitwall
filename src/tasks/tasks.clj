(ns tasks.tasks
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [ring.util.response :as response]))

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
