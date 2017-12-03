(ns advent2017.day2
  (require [clojure.string :as str]))

;For each row, determine the difference between the largest value and the smallest value; the checksum is the sum of all of these differences.
;
;For example, given the following spreadsheet:
;
;5 1 9 5
;7 5 3
;2 4 6 8
;The first row's largest and smallest values are 9 and 1, and their difference is 8.
;The second row's largest and smallest values are 7 and 3, and their difference is 4.
;The third row's difference is 6.
;In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.

(def input (slurp "resources/day2input.txt"))

(defn make-table [input]
  (map #(map read-string (str/split % #"\t")) (str/split-lines input)))

(defn checksum [row]
  (- (apply max row) (apply min row)))

(defn -main1 [input]
  (reduce + (map checksum (make-table input))))

;; PART TWO
;
;the goal is to find the only two numbers in each row where one evenly divides the other - that is, where the result of the division operation is a whole number. They would like you to find those numbers on each line, divide them, and add up each line's result.
;
;For example, given the following spreadsheet:
;
;5 9 2 8
;9 4 7 3
;3 8 6 5
;In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
;In the second row, the two numbers are 9 and 3; the result is 3.
;In the third row, the result is 2.
;In this example, the sum of the results would be 4 + 3 + 2 = 9.

(defn check-divisible [row]
  (reduce +
    (for [x row y row
          :when (not= x y)]
      (if (zero? (mod x y))
        (/ x y)
        0))))

(defn -main2 [input]
  (reduce + (map check-divisible (make-table input))))