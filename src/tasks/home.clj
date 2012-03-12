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

(declare webengage-script)

;;FIXME: github request not required for xhr requests
(defn gitwall [project session]
  (let [user (login/github-or-anonymous-user session)
        user-type (login/github-or-anonymous-user-type session)
        project (or project "default")
        tasks (task/find-all-by user project)]
    {:body
     (html
      [:html
       [:head
        (default-javascripts)
        (default-stylesheets)
        (webengage-script)]
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
                             (if (and (= "default" project) (empty? tasks))
                               sample/sample-tasks
                               tasks))]]]]]])
     :session
     (assoc session user-type user)}))

(defn webengage-script []
  (html
   [:webengage {:license "d3a49d18"}
    (str "<script id='_webengage_script_tag' type='text/javascript'>
(function(){var _we = document.createElement('script');_we.type = 'text/javascript';_we.async = true;var _weWidgetJs = '/js/widget/webengage-min-v-2.0.js';if(document.location.protocol == 'https:'){_we.src='//ssl.widgets.webengage.com' +_weWidgetJs;}else{_we.src='//cdn.widgets.webengage.com' +_weWidgetJs;} var _sNode = document.getElementById('_webengage_script_tag');_sNode.parentNode.insertBefore(_we, _sNode);})();</script>")]))