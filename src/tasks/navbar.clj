(ns tasks.navbar
  (:use compojure.core
        hiccup.core
        tasks.assets))

(defn navbar []
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
              [:a {:href "#"} (str "Home")]]
             [:li {:class "active"}
              [:a {:href "#"} (str "About")]]
             [:li {:class "active"}
              [:a {:href "#"} (str "Contact")]]]]]]]))
