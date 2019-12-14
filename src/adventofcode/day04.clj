; day04
(ns adventofcode.day04
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn]))

(def START 152085)
(def END   670283)

(defn has-groups-of-size
  "Returns true if coll contains at least one group having the given size."
  ([group-size coll]
   (some #(= group-size (count %)) coll)))

(defn get-adjacent-digit-pairs
  ([number] (get-adjacent-digit-pairs 2 number))
  ([pair-size number]
   (partition pair-size 1 (str number))))

(defn has-adjacent-digits?
  "Checks if any two adjacent digits in 'value' are the same."
  ([value] (has-adjacent-digits? 2 value))
  ([pair-size value]
   (let [pairs (get-adjacent-digit-pairs pair-size value)]
     (some #(apply = %) pairs))))

(defn get-adjacent-groups
  [group-size coll]
  (partition group-size 1 coll))

(defn has-adjacent-pairs?
  "Checks if any two adjacent pairs are the same."
  [pairs]
  (some #(apply = %) pairs))

(defn split-number-into-digits
  "Converts the given number into a sequence of its digits."
  [number] (map #(Character/digit % 10) (str number)))

(defn number-of-adjacent-digits
  "Returns the number of occurences of groups of adjacent digits 
   only containing the same digit."
  [group-size number]
  (count (filter #(apply = %)
                 (get-adjacent-digit-pairs group-size number))))

(def no-triples?
  "Checks if a number has no pairs of threes"
  (complement (partial has-adjacent-digits? 3)))

(defn monotononical-non-decreasing-digits? [value]
  "Checks if two adjacent digits are either in increasing order or the same"
  (let [pairs (map (fn [[v1 v2]] [(int v1) (int v2)]) (get-adjacent-digit-pairs value))]
    (every? #(apply <= %) pairs)))

(defn has-doubles?
  "Tests if number has double digits in it. Tripple or larger groups don't count."
  [number]
  (->> (split-number-into-digits number)
       (partition-by identity)
       (has-groups-of-size 2)))

(defn day04-part1 []
  (count
   (filter (every-pred
            has-adjacent-digits?
            monotononical-non-decreasing-digits?)
           (range START (inc END)))))

(defn day04-part2 []
  (count
   (filter (every-pred
            has-doubles?
            monotononical-non-decreasing-digits?)
           (range START (inc END)))))

(day04-part2)