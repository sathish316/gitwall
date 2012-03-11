(ns tasks.sample)

(def sample-statuses
  [{:code 1, :name "Todo"}
   {:code 2, :name "Doing" :bg-color "#F7DFDA"}
   {:code 3, :name "Done" :bg-color "#B3F5D6"}])

(def sample-tasks
  [
   {:id 201 :title "Make cards draggable" :status 1}
   {:id 202 :title "Make cards droppable" :status 1}
   {:id 203 :title "Make cards sortable" :status 1}
   ])

