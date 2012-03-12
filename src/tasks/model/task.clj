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
        tasks (project/get-tasks proj)
        task-id (mongo_counter/get-next-id :tasks)
        task {:id task-id
              :title task-title
              :status (:code status)
              :order task-id}]
    (project/update-tasks proj (conj tasks task))
    task))

(defn update-task [user project-name task-id attrs]
  (let [proj (project/find-or-create-project user project-name)
        tasks (project/get-tasks proj)
        task (project/find-task-by-id proj task-id)
        modified-task (merge task attrs)]
    (project/update-tasks proj
                          (conj (remove #(= task-id (:id %)) tasks)
                                modified-task))))

(defn delete-task [user project-name task-id]
  (let [proj (project/find-or-create-project user project-name)
        tasks (project/get-tasks proj)
        task (project/find-task-by-id proj task-id)]
    (project/update-tasks proj
                          (remove #(= task-id (:id %)) tasks))))