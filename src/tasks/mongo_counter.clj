(ns tasks.mongo_counter
  (use somnium.congomongo))

(defn find-or-create-counter [name]
  (let [attrs {:_id name}]
    (or (fetch-one :counters :where attrs)
        (insert! :counters (merge attrs {:count 0})))))

(defn get-next-id [counter-name]
  (let [counter (find-or-create-counter counter-name)]
    (update! :counters counter {:$inc {:count 1}})
    (:count counter)))
  
