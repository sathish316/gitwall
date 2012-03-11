(ns tasks.model.project
  (use somnium.congomongo))

(defn find-or-create-project [user project-name]
  (let [attrs {:user user :name project-name}]
    (or (fetch-one :projects :where attrs)
        (insert! :projects attrs))))