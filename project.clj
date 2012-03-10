(defproject Tasks "1.0.0-SNAPSHOT"
  :description "A simple tasklist in compojure"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [compojure "1.0.1"]
                 [hiccup "0.3.7"]
                 [clj-oauth2 "0.2.0"]]
  :dev-dependencies [[lein-ring "0.5.4" :exclusions [hiccup]]]
  :ring {:handler tasks.core/app})