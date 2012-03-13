(ns tasks.navbar
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [tasks.oauth_github :as oauth_github]))

(defn github-username [session]
  (or (session :github_username)
      "Sign in"))

(defn navbar [session]
  (html
   [:div {:class "navbar navbar-fixed-top"}
      [:div {:class "navbar-inner"}
        [:div {:class "container"}
          [:a {:class "btn btn-navbar" :data-toggle "collapse" :data-target ".nav-collapse"}
            [:span {:class "icon-bar"}]
            [:span {:class "icon-bar"}]
            [:span {:class "icon-bar"}]]
          [:a {:class "brand" :href "#"} (str "Gitwall")]
          [:div {:class "nav-collapse"}
            [:ul {:class "nav"}
             [:li {:class "active"}
              [:a {:id "github-sign-in" :href "/login"}
               (image {:class "github-logo"} "img/github_32.png")
               [:span {:class "github-username"}
                (github-username session)]]]]]]]]))

