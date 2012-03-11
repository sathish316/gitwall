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
            [tasks.sample :as sample]))

(defn gitwall [session]
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
         (tasks/task-wall sample/sample-statuses (session :tasks))]]]]]]))
         ;(tasks/list-tasks (:tasks session))]]]]]]))

