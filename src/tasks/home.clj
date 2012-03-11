(ns tasks.home
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [ring.util.response :as response]
            [tasks.tasks :as tasks]
            [tasks.projects :as projects]
            [tasks.navbar :as navbar]
            [tasks.oauth_github :as oauth_github]
            [tasks.github :as github]
            [tasks.sample :as sample]
            [tasks.model.task :as task]
            [tasks.login :as login]))

;;FIXME: github request not required for xhr requests
(defn gitwall [project session]
  (let [user (login/github-or-anonymous-user session)
        user-type (login/github-or-anonymous-user-type session)]
    {:body
     (html
      [:html
       [:head
        (default-javascripts)
        (default-stylesheets)]
       [:body
        (navbar/navbar session)
        [:div {:class "container"}
         [:div {:class "row projects-and-tasks-row"}
          [:div {:class "span2 projects-container"}
           [:h3 "Projects"]
           (projects/list-projects (github/repos (:access_token session)))
           (form-to {:id "github-sign-in-form"} [:post (:uri oauth_github/auth-req)])]
          [:div {:class "span10 wall-container"}
           [:h3 "Tasks"]
           [:div
            (tasks/new-task)
            (tasks/task-wall sample/sample-statuses
                             (task/find-all-by user (or project "default")))]]]]]])
     :session
     (assoc session user-type user)}))
