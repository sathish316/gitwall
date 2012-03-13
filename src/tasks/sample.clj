(ns tasks.sample)

(def sample-statuses
  [{:code 1, :name "Todo"}
   {:code 2, :name "Doing" :bg-color "#F7DFDA"}
   {:code 3, :name "Done" :bg-color "#B3F5D6"}])

(def sample-tasks
  [
   {:id 201 :title "Add New Task" :status 1 :class "sample_task"}
   {:id 202 :title "Sign in to Github to access Projects" :status 1 :class "sample_task"}
   {:id 203 :title "Completed task" :status 3 :class "sample_task"}
   {:id 204 :title "Task can be deleted using this icon" :status 1 :class "sample_task sample_delete_task" }
   ])

