(ns tasks.projects
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets))

(defn project-link [project]
  (html
   [:li
    [:a project]]))

(defn list-projects [projects]
  (html
   [:ul
    (map project-link projects)]))

(defn get-projects-for-user [] ;TODO for user
  ["Shopping cart" "Super Mario" "iPad Whiteboard" "Facebook app"])
  
