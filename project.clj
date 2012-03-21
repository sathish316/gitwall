(defproject Tasks "1.0.0-SNAPSHOT"
  :description "Gitwall is a Card wall/Todo list for all your Github projects"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [compojure "1.0.1"]
                 [hiccup "0.3.7"]
                 [clj-oauth2 "0.2.0"]
                 [clj-http "0.3.3"]
                 [org.clojure/data.json "0.1.2"]
                 [congomongo "0.1.8"]
                 [ring/ring-jetty-adapter "1.1.0-SNAPSHOT"]]
  :dev-dependencies [[lein-ring "0.5.4" :exclusions [hiccup]]]
  :ring {:handler tasks.web/app})