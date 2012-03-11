(ns tasks.assets
  (:use compojure.core
        hiccup.core
        hiccup.page-helpers)
  (:require [tasks.sample :as sample]))

(declare card-styles)

(defn default-javascripts []
  (html
   (include-js "/js/jquery-1.7.1.min.js")
   (include-js "/js/application.js")
   (include-js "/js/jquery-ui-1.8.18.custom.min.js")
   (include-js "/js/bootstrap.js")))

(defn default-stylesheets []
  (html
   [:style {:type "text/css"}
    (card-styles sample/sample-statuses)]
   (include-css "/css/bootstrap.min.css")
   (include-css "/css/bootstrap-responsive.min.css")
   (include-css "/css/jquery-ui-1.8.18.custom.css")
   (include-css "/css/application.css")))

(defn card-colors [statuses]
  (let [default-bg-color "#C6E7F7"
        default-fg-color "#000000"]
    (map (fn [status]
           {:class (str "card_" (:code status))
            :bg-color (or (:bg-color status) default-bg-color)
            :fg-color (or (:fg-color status) default-fg-color)})
         statuses)))

(defn card-styles [statuses]
  (clojure.string/join
   " "
   (map (fn [style]
          (str "." (:class style) " {"
               " background-color: " (:bg-color style) ";"
               " color: " (:fg-color style) ";"
               "}"))
        (card-colors statuses))))
