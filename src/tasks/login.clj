(ns tasks.login
  (:use compojure.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        tasks.assets)
  (:require [tasks.oauth_github :as oauth_github]
            [ring.util.response :as response]))

(defn login-page []
  (html
   [:html
    [:head
     (default-stylesheets)]
    [:body
     [:div
      [:h2 "Github Tasks"]
      (form-to [:post (:uri oauth_github/auth-req)]
               (submit-button "Sign in using Github"))]]]))

(defn store-access-token-and-redirect-to-tasks [params session]
  (let [access-token (oauth_github/access-token params)
        session (assoc session :access_token access-token)]
    (-> (response/redirect "/tasks")
        (assoc :session session))))