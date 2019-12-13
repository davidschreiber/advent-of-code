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


; Wire creation
(defn get-steps [command]
  (edn/read-string (subs command 1)))

(defn produce-steps-from
  [start-coordinate command]
  (let [[x y] start-coordinate
        direction (subs command 0 1)
        steps (get-steps command)]
    (case direction
      "U" (partition 2
                     (interleave
                      (repeat (+ steps 1) x)
                      (range y (- y steps 1) -1)))
      "D" (partition 2
                     (interleave
                      (repeat (+ steps 1) x)
                      (range y (+ y steps 1) 1)))
      "R" (partition 2
                     (interleave
                      (range x (+ x steps 1) 1)
                      (repeat (+ steps 1) y)))
      "L" (partition 2
                     (interleave
                      (range x (- x steps 1) -1)
                      (repeat (+ steps 1) y))))))

(defn create-wire
  ([command-list] (create-wire [[0 0]] command-list))
  ([current-wire command-list]
   (if (empty? command-list)
     current-wire
     (let [command (first command-list)
           end-point (last current-wire)
           new-wire (concat current-wire
                            (rest (produce-steps-from end-point command)))]
       (create-wire new-wire (rest command-list))))))

; Wire testing
(defn find-intersections
  [wire1 wire2]
  (let [wire-set1 (set wire1)
        wire-set2 (set wire2)]
    (clojure.set/intersection wire-set1 wire-set2)))

; Input parsing
(defn read-input-file [] (slurp "day3.txt"))

(defn parse-wire-command
  "Takes the raw input file content and returns the actual wires in coordinates"
  [file-content]
  (->> (str/split-lines file-content)
       (map #(str/split % #","))
       (map #(create-wire %))))

; Program logic

(defn day3-part1 []
  (let [wires (parse-wire-command (read-input-file))]
    (second
     (sort
      (map (partial manhattan-distance [0 0])
           (find-intersections (first wires) (second wires)))))))
; (parse-wire-command (read-input-file))
; day03, part 1

; day03, part 2
