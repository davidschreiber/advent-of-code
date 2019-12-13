(ns adventofcode.day03-test
  (:require [clojure.test :refer :all]
            [adventofcode.day03 :refer :all]))


(deftest test-scalar-distance
  (testing "Scalar distance of values"
    (is (= 0 (scalar-distance 0 0)))
    (is (= 0 (scalar-distance 1 1)))
    (is (= 0 (scalar-distance 10 10)))
    (is (= 10 (scalar-distance 10 0)))
    (is (= 10 (scalar-distance 0 10)))
    (is (= 5 (scalar-distance 2 7)))
    (is (= 5 (scalar-distance 7 2)))))

(deftest test-horizontal-distance
  (testing "Calculate horizontal distance"
    (is (= 0  (horizontal-distance [0  1] [0 0])))
    (is (= 0 (horizontal-distance  [0 10] [0 0])))
    (is (= 2 (horizontal-distance  [4  8] [6 0])))
    (is (= 2 (horizontal-distance  [6  0] [4 8])))
    (is (= 0  (horizontal-distance [6  8] [6 8])))))

(deftest test-vertical-distance
  (testing "Calculate manhattan distance"
    (is (= 1  (vertical-distance [0  1] [0 0])))
    (is (= 10 (vertical-distance [0 10] [0 0])))
    (is (= 8  (vertical-distance [4  8] [6 0])))
    (is (= 0  (vertical-distance [6  8] [6 8])))))

(deftest test-manhattan-distance
  (testing "Calculate manhattan distance"
    (is (= 1  (manhattan-distance [0  1] [0 0])))
    (is (= 10 (manhattan-distance [0 10] [0 0])))
    (is (= 10 (manhattan-distance [4  8] [6 0])))
    (is (= 0  (manhattan-distance [6 8] [6 8])))))

(deftest test-get-steps
  (testing "Extracts steps"
    (is (= 10 (get-steps "R10")))
    (is (= 0 (get-steps "R0")))
    (is (= 3 (get-steps "R3")))))

(deftest test-produce-steps-from
  (testing "Step coordinates right"
    (is (= [[0 0] [1 0] [2 0]] (produce-steps-from [0 0] "R2")))
    (is (= [[0 0] [1 0] [2 0] [3 0]] (produce-steps-from [0 0] "R3")))
    (is (= [[10 5] [11 5] [12 5] [13 5]] (produce-steps-from [10 5] "R3"))))

  (testing "Step coordinates right"
    (is (= [[0 0] [-1 0] [-2 0]] (produce-steps-from [0 0] "L2")))
    (is (= [[0 0] [-1 0] [-2 0] [-3 0]] (produce-steps-from [0 0] "L3")))
    (is (= [[10 5] [9 5] [8 5] [7 5]] (produce-steps-from [10 5] "L3"))))

  (testing "Step coordinates down"
    (is (= [[0 0] [0 1] [0 2]] (produce-steps-from [0 0] "D2")))
    (is (= [[0 0] [0 1] [0 2] [0 3]] (produce-steps-from [0 0] "D3")))
    (is (= [[10 5] [10 6] [10 7]] (produce-steps-from [10 5] "D2"))))

  (testing "Step coordinates up"
    (is (= [[0 0] [0 -1] [0 -2]] (produce-steps-from [0 0] "U2")))
    (is (= [[0 0] [0 -1] [0 -2] [0 -3]] (produce-steps-from [0 0] "U3")))
    (is (= [[10 5] [10 4] [10 3]] (produce-steps-from [10 5] "U2")))))

(deftest test-produce-steps-from
  (testing "Create steps from"
    (is (= [[0 0] [1 0] [2 0]] (produce-steps-from [0 0] "R2")))
    (is (= [[0 0] [1 0] [2 0] [3 0]] (produce-steps-from [0 0] "R3")))))

(deftest test-create-wire
  (testing "Turns wire commands into wire"
    (is (= [[0 0] [1 0] [2 0]] (create-wire ["R2"])))
    (is (= [[0 0] [1 0] [2 0] [2 -1] [2 -2]] (create-wire ["R2", "U2"]))))

  (testing "Walking in circles"
    (is (= [0 0] (last (create-wire ["R2", "U2", "L2", "D2"]))))))

(deftest test-parse-wire-commands
  (testing "Wire command string is turned into array of movement vectors."))

(deftest test-find-wire-intersections
  (testing "Wires without intersections"
    (is (= #{} (find-intersections [[0 0] [0 1]] [[1 0] [1 1]])))
    (is (= #{[0 1]} (find-intersections [[0 0] [0 1]] [[1 0] [1 1] [0 1]])))))
