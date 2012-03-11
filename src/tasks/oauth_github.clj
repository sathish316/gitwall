(ns tasks.oauth_github
  (:require [clj-oauth2.client :as oauth2]))

(def github-oauth2
  {:authorization-uri "https://github.com/login/oauth/authorize"
   :access-token-uri "https://github.com/login/oauth/access_token"
   :redirect-uri "http://localhost:3000/github_oauth2_callback"
   :client-id "b53a57b4596e648978bd"
   :client-secret "0064f60f8e86e261037813185f496be679e4e6b5"
   :access-query-param :access-token
   :grant-type "authorization_code"})

(def auth-req
  (oauth2/make-auth-request github-oauth2 nil))

(defn access-token [auth-resp]
  (oauth2/get-access-token github-oauth2 auth-resp auth-req))