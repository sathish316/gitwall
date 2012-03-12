(ns tasks.tasks
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets
        somnium.congomongo)
  (:require [ring.util.response :as response]
            [tasks.projects :as projects]
            [tasks.navbar :as navbar]
            [tasks.oauth_github :as oauth_github]
            [tasks.github :as github]
            [tasks.sample :as sample]
            [tasks.model.task :as task]
            [tasks.model.project :as project]
            [tasks.login :as login]))

(defn store-access-token-and-redirect-to-tasks [params session]
  (let [access-token (:access-token (oauth_github/access-token params))
        anonymous-username (session :anonymous_username)
        github-username (github/user_login access-token)
        session (assoc session
                  :access_token access-token
                  :github_username github-username)]
    (project/migrate anonymous-username github-username)
    (-> (response/redirect "/tasks")
        (assoc :session session))))

(defn new-task []
  (html
   (form-to {:id "new_task_form" :autocomplete "off"} [:post "/tasks"]
            [:div {:class "controls"}
             (text-field {:class "input-xlarge add-task-field"} :task)
             (hidden-field :project "default")
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
   [:li {:class (str "card card_" (:status task) " " (:class task))
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

(defn initial-status []
  (reduce #(if (< (:code %1)
                  (:code %2))
             %1 %2)
          sample/sample-statuses))

(defn create-task [project task session]
  (let [status (initial-status)
        user (login/github-or-anonymous-user session)
        project (or project "default")
        new-task (task/insert-task user project task status)]
    {:body (task-card status new-task)}))

(defn update-task [project task-id status session]
  (let [user (login/github-or-anonymous-user session)
        project (or project "default")]
    (task/update-task user project task-id
                      {:status status})
    {:status 200}))