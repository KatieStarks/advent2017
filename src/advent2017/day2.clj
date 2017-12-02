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
  (let [mn (apply min row)
        mx (apply max row)]
        (- mx mn)))

(defn -main [input]
  (map checksum (make-table input)))