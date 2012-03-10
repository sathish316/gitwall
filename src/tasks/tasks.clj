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

(defn new-task []
  (html
   (form-to {:id "new_task_form"} [:post "/tasks"]
            [:div {:class "controls"}
             (text-field {:class "input-xlarge add-task-field"} :task)
             (submit-button {:class "btn-primary add-task-btn"} "Add")
             ])))

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
