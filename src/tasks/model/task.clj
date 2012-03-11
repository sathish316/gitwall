(ns tasks.model.task
  (use somnium.congomongo)
  (:require [tasks.model.project :as project]
            [tasks.mongo_counter :as mongo_counter]))

(defn find-all-by [user project-name]
  (if (and user project-name)
    (let [proj (project/find-or-create-project user project-name)]
      (:tasks proj))
    []))
   
(defn insert-task [user project-name task-title status]
  (let [proj (project/find-or-create-project user project-name)
        tasks (or (:tasks proj) [])
        task-id (mongo_counter/get-next-id :tasks)
        task {:id task-id
              :title task-title
              :status (:code status)
              :order task-id}]
    (update! :projects proj (merge proj {:tasks (conj tasks task)}))
    task))

