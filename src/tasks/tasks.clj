(ns tasks.tasks
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [ring.util.response :as response]
            [tasks.projects :as projects]
            [tasks.navbar :as navbar]
            [tasks.oauth_github :as oauth_github]
            [tasks.github :as github]))

(defn store-access-token-and-redirect-to-tasks [params session]
  (let [access-token (:access-token (oauth_github/access-token params))
        session (assoc session
                  :access_token access-token
                  :github_username (github/user_login access-token))]
    (-> (response/redirect "/tasks")
        (assoc :session session))))

(declare new-task)
(declare list-tasks)

(defn gitwall [session]
  (html
   [:html
    [:head
     (default-javascripts)
     (default-stylesheets)]
    [:body
     (navbar/navbar session)
     [:div {:class "container"}
      [:div {:class "row"}
       [:div {:class "span2 projects-container"}
        [:h3 "Projects"]
        (projects/list-projects (projects/get-projects-for-user))
        (form-to {:id "github-sign-in-form"} [:post (:uri oauth_github/auth-req)])]
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
