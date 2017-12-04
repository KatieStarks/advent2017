(ns advent2017.day3)

;17  16  15  14  13
;18   5   4   3  12
;19   6   1   2  11
;20   7   8   9  10
;21  22  23---> ...
;While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.
;
;For example:
;
;Data from square 1 is carried 0 steps, since it's at the access port.
;Data from square 12 is carried 3 steps, such as: down, left, left.
;Data from square 23 is carried only 2 steps: up twice.
;Data from square 1024 must be carried 31 steps.

(def input 277678)

(def test-inputs [1 12 23 1024])

(defn ** [x]
  (* x x))

(defn abs [x]
  (if (pos? x)
    x
    (- x)))

(defn find-perimeter [len]
  (+ (* 2 len) (* 2 (- len 2))))

(defn find-ring [y]
  (int (Math/ceil (/ (- (Math/sqrt y) 1) 2))))

(defn find-ring-corner [ring-num]
  (int (Math/pow (+ (* 2 ring-num) 1) 2)))

(defn pos-from-mid [num-pos side mid-pos]
  (if (<= num-pos side)
    (abs (- num-pos mid-pos))
    (pos-from-mid (- num-pos (+ side 1)) side mid-pos)))

(defn -main [input]
  (let [ring (find-ring input)
        ring-corner (find-ring-corner ring)
        sqrt (int (Math/sqrt ring-corner))
        last-ring-corner (int (** (- sqrt 2)))
        start-num (+ last-ring-corner 1)
        perimeter (find-perimeter sqrt)
        side (- sqrt 1)
        mid (int (Math/ceil (/ sqrt 2)))
        mid-pos (- mid 2)
        num-pos (- input start-num)
        steps-from-mid (pos-from-mid num-pos (- side 1) mid-pos)]
    (+ ring steps-from-mid)))

;As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.
;
;So, the first few squares' values are chosen as follows:
;
;Square 1 starts with the value 1.
;Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
;Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
;Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
;Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
;Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:
;
;147  142  133  122   59
;304    5    4    2   57
;330   10    1    1   54
;351   11   23   25   26
;362  747  806--->   ...
;What is the first value written that is larger than your puzzle input?

(def spiral (atom {}))
(def pos (atom {:x 0 :y 0}))

(def first-val 1)

(swap! spiral assoc {:x 0 :y 0} first-val)

(defn build-val [x y]
  (let [spiral @spiral
        v (reduce + (for [xs [(dec x) x (inc x)]
                          ys [(dec y) y (inc y)]]
                      (get spiral {:x xs :y ys} 0)))]
    (if (> v input)
      (println v)
      v)
    )
  )

(defn build-next [x y]
  (swap! spiral assoc {:x x :y y} (build-val x y))
  (swap! pos assoc :x x :y y))

(defn move [dir]
  (let [x (@pos :x)
        y (@pos :y)]

    (case dir 0 (build-next (inc x) y)                      ;;right
              1 (build-next x (inc y))                      ;;up
              2 (build-next (dec x) y)                      ;;left
              3 (build-next x (dec y))                      ;;down
              )))

;(defn update-dir []
;  (if (= @dir 3)
;    (swap! dir 0)
;    (swap! dir inc)))

(defn build-spiral [n]
  ;; step how many times i call dir with the same val
  ; dir change every time done with # of steps
  ; inc-step after 2 dirs (if 2 inc step, if 0 inc step)
  (loop [steps 1
         dir 0
         counter 0]
    (dotimes [_ steps] (move dir))
    (dotimes [_ steps] (move (mod (inc dir) 4)))
    (if (< counter n)
      (recur (inc steps) (mod (+ dir 2) 4) (+ counter (* steps 2)))
      )))

;(move 0 0 0)
;(move 1 1 0)