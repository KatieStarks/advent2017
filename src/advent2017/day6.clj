(ns advent2017.day6)

(def input [4 10 4 1 8 4 9 14 5 1 14 15 0 15 3 5])

(def results (atom input))

(defn inc-n [coll n]
  (update coll n inc))

(defn update-index [n]
  (swap! results inc-n n)
  )

(defn redistribute [result]
  (let [val (apply max result)
        n (.indexOf result val)]
    (swap! results assoc n 0)
    (dotimes [i val] (update-index (mod (+ i n 1) (count result))))
    @results))

(defn -main1 []
  (loop [prev-results #{}
         memory @results]
    (if (contains? prev-results memory)
      (count prev-results)
      (recur (conj prev-results memory) (redistribute memory)))))

(defn -main2 []
  (let [new-results (loop [prev-results #{}
                           memory @results]
                      (if (contains? prev-results memory)
                        (last prev-results)   ;; updated for part two
                        (recur (conj prev-results memory) (redistribute memory))))]
    (reset! results new-results)
    (-main1)))