(ns tasks.core
  (:use compojure.core
        hiccup.core)
  (:require [compojure.handler :as handler]))

(defn display []
  (html [:h1 "Hello world"]))

(defroutes myroutes
  (GET "/" [] (display)))

(def app
  (handler/site myroutes))