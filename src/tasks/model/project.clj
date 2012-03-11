(ns tasks.model.project
  (use somnium.congomongo))

(defn find-or-create-project [user project-name]
  (let [attrs {:user user :name project-name}]
    (or (fetch-one :projects :where attrs)
        (insert! :projects attrs))))

(defn find-task-by-id [project task-id]
  (first (filter #(= (:id %) task-id)
                 (:tasks project))))

(defn get-tasks [project]
  (or (:tasks project)
      []))

(defn update-tasks [project tasks]
  (update! :projects project
           (merge project {:tasks tasks})))

(defn migrate [from-user to-user]
  (if from-user
    (let [anonymous-project (fetch-one :projects :where {:user from-user})
          github-project (fetch-one :projects :where {:user to-user})]
      (if (nil? github-project)
        (update! :projects anonymous-project
                 (merge anonymous-project {:user to-user}))))))
           