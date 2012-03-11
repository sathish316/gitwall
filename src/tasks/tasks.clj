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
   (form-to {:id "new_task_form" :autocomplete "off"} [:post "/tasks"]
            [:div {:class "controls"}
             (text-field {:class "input-xlarge add-task-field"} :task)
             (submit-button {:class "btn-primary add-task-btn"} "Add")
             ])))

(defn task-link [task]
  (html
   [:li
    task]))
    
(defn list-tasks [tasks]
  (html
   [:div {:id "tasks"}
    [:ul {:class "unstyled"}
     (map task-link tasks)]]))

(defn task-card [status task]
  (html
   [:li {:class (str "card card_" (:status task))
         :id (str "task-card-" (:id task))}
    (:title task)]))

(defn task-column [[status tasks]]
  [:div {:class "span2"}
   [:h3 (:name status)]
   [:ul {:class "task-column unstyled"
         :id (str "task-column-" (:code status))}
    (map #(task-card status %) tasks)]])

(defn group-tasks-by-status [statuses tasks]
  (let [tasks-by-status-code (group-by :status tasks)]
    (map (fn [status]
           [status (tasks-by-status-code (:code status))])
         statuses)))

(defn task-wall [statuses tasks]
   (html
   [:div {:id "wall"}
     (map task-column (group-tasks-by-status statuses tasks))]))

(defn create-task [task session]
  (let [tasks (session :tasks)
        result (if (vector? tasks)
                 (conj tasks task)
                 (vector task))]
    {:body (list-tasks result)
     :session {:tasks result}}))
