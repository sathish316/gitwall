(ns tasks.oauth_github
  (:require [clj-oauth2.client :as oauth2]))

(def github-oauth2
  {:authorization-uri "https://github.com/login/oauth/authorize"
   :access-token-uri "https://github.com/login/oauth/access_token"
   :redirect-uri "http://localhost:3000/github_oauth2_callback"
   :client-id "91dc35fe530ab2a358b2"
   :client-secret "4914f9d2be376e4579e04ef203ebe37af4079f2b"
   :access-query-param :access-token
   :grant-type 'authorization-code})

(def auth-req
  (oauth2/make-auth-request github-oauth2 "abcdef"))

;(def access-token
;  (oauth2/get-access-token github-oauth2 auth-resp auth-req))