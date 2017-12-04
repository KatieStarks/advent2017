(ns advent2017.day4
  (require [clojure.string :as str]))

;A new system policy has been put in place that requires all accounts to use a
; passphrase instead of simply a password. A passphrase consists of a series of
; words (lowercase letters) separated by spaces.
;
;To ensure security, a valid passphrase must contain no duplicate words.
;
;For example:
;
;aa bb cc dd ee is valid.
;aa bb cc dd aa is not valid - the word aa appears more than once.
;aa bb cc dd aaa is valid - aa and aaa count as different words.

;The system's full passphrase list is available as your puzzle input.
; How many passphrases are valid?

(def input (slurp "resources/day4input.txt"))
(def test-pass "aa bb cc dd aaa")
(def test-fail "aa bb cc dd aa")

(defn separate-passes [input]
  (map #(str/split % #" ") (str/split-lines input)))

(defn find-diff [input]
  (if (= (count (distinct input))
         (count input))
    1
    0))

(defn -main [input]
  (reduce + (map find-diff (separate-passes input))))