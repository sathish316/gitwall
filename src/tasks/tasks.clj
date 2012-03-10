(ns tasks.tasks
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [ring.util.response :as response]
            [tasks.projects :as projects]))

(declare new-task)
(declare list-tasks)

(defn gitwall [session]
  (html
   [:html
    [:head
     (default-javascripts)
     (default-stylesheets)]
    [:body
     [:div {:class "container-fluid"}
      [:div {:class "row-fluid"}
       [:div {:class "span2 projects-container"}
        [:h3 "Projects"]
        (projects/list-projects (projects/get-projects-for-user))]
       [:div {:class "span10 wall-container"}
        [:h3 "Tasks"]
        (new-task)
        (list-tasks session)]]]]]))

(defn new-task []
  (html
   (form-to {:id "new_task_form"} [:post "/tasks"]
            (text-field :task)
            (submit-button {:class "btn-primary"} "Add"))))

(defn list-tasks [session]
  (html
   [:div {:id "tasks"}
    (unordered-list (:tasks session))]))

(defn create-task [task session]
  (let [tasks (session :tasks)
        result (if (vector? tasks)
                 (conj tasks task)
                 (vector task))]
    {:body (html (unordered-list result))
     :session {:tasks result}}))
