(ns tasks.assets
  (:use compojure.core
        hiccup.core
        hiccup.page-helpers))

(defn default-javascripts []
  (html
   (include-js "/js/jquery.min.js")
   (include-js "/js/application.js")
   (include-js "/js/jquery.form.js")
   (include-js "/js/bootstrap.js")))

(defn default-stylesheets []
  (html
   (include-css "/css/bootstrap.min.css")
   (include-css "/css/bootstrap-responsive.min.css")))
