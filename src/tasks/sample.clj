(ns tasks.sample)

(def sample-statuses
  [{:code 1, :name "Todo"}
   {:code 2, :name "Doing"}
   {:code 3, :name "Done" :bg-color "#B3F5D6" :fg-color "#4334CF"}])

(def sample-tasks
  [{:id 101 :title "Multiple todo lists" :status 1}
   {:id 102 :title "Customize Lanes" :status 1}
   {:id 201 :title "Make cards draggable" :status 1}
   {:id 202 :title "Make cards droppable" :status 1}
   {:id 203 :title "Make cards sortable" :status 1}
   {:id 103 :title "Add wall" :status 2}
   {:id 104 :title "Github Sign in" :status 3}
   {:id 105 :title "Load Github projects" :status 3}])  
