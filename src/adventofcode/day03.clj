; day03
(ns adventofcode.day03
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn]))

; Generic
(defn x "x component of two dimensional carthesian vector" [point] (first point))
(defn y "y component of two dimensional carthesian vector" [point] (second point))

; Distance calculations
(defn scalar-distance
  "Calculates the distance between the two given values."
  [scalar1 scalar2] (Math/abs (- scalar1 scalar2)))

(defn component-distance [point1 point2 component]
  (scalar-distance (component point1) (component point2)))

(defn horizontal-distance
  "Calculates the horizontal distance between the two points."
  [point1 point2] (component-distance point1 point2 x))

(defn vertical-distance
  "Calculates the vertical distance between the two points"
  [point1 point2] (component-distance point1 point2 y))

(defn manhattan-distance
  "Manhattan distance of the two given points."
  [p1 p2]
  (+ (horizontal-distance p1 p2) (vertical-distance p1 p2)))

; Line creation
(defn create-line
  "Create a line using the given start point and movement vector. The
   returned line is of the form [start-point end-point]"
  [start-point movement-vector]
  (let [[x1 y1] start-point [vx vy] movement-vector]
    [[x1 y1] [(+ x1 vx) (+ y1 vy)]]))

; Input parsing
(defn read-input-file [] (slurp "day3.txt"))

(defn parse-wire-command [file-content]
  (->> (str/split-lines file-content)
       (map #(str/split % #","))
       (loop []))

(defn to-movement-vector 
  "Takes a single movement command (for example 'U10') and turns it into
   a 2-dimensional movement vector in the form of [x y]."
  [movement-command]
  (let [direction (subs movement-command 0 1)
        steps (edn/read-string (subs movement-command 1))]
    (case direction
      "U" [0 (unchecked-negate steps)]
      "D" [0 steps]
      "R" [steps 0]
      "L" [(unchecked-negate steps) 0])))

; Program logic

(parse-wire-command (read-input-file))
; day03, part 1

; day03, part 2
