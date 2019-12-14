; day04
(ns adventofcode.day04
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn]))

(def START 152085)
(def END   670283)

(defn has-groups-matching
  "Returns true if coll contains at least one group having the given size."
  ([predicate coll]
   (some predicate coll)))

(defn split-number-into-digits
  "Converts the given number into a sequence of its digits."
  [number] (map #(Character/digit % 10) (str number)))

(defn monotononical-non-decreasing-digits? 
  [number]
  "Checks any two adjacent digits are either in increasing order or the same"
  (->> (split-number-into-digits number)
       (partition 2 1)
       (every? #(apply <= %))))
  
(defn has-doubles-or-larger?
  [number]
  (->> (split-number-into-digits number)
       (partition-by identity)
       (has-groups-matching #(<= 2 (count %)))))

(defn has-doubles?
  "Tests if number has double digits in it. Tripple or larger groups don't count."
  [number]
  (->> (split-number-into-digits number)
       (partition-by identity)
       (has-groups-matching #(= 2 (count %)))))

(defn day04-part1 []
  (count
   (filter (every-pred
            has-doubles-or-larger?
            monotononical-non-decreasing-digits?)
           (range START (inc END)))))

(defn day04-part2 []
  (count
   (filter (every-pred
            has-doubles?
            monotononical-non-decreasing-digits?)
           (range START (inc END)))))

(day04-part2)