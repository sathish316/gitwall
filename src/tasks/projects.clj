(ns tasks.projects
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets))

(defn project-link [project]
  (html
   [:li {:class "project-link"}
    [:a {:title (:description project) :href "#"}
     (:name project)]]))

(defn list-projects [projects]
  (html
   [:ul {:class "nav nav-pills nav-stacked"}
    (map project-link projects)]))
  
