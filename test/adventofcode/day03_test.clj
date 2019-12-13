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

(deftest test-create-line
  (testing "Create line"
    (is (= [[0 0] [10 20]] (create-line [0 0] [10 20])))
    (is (= [[10 20] [10 20]] (create-line [10 20] [0 0])))
    (is (= [[10 20] [16 24]] (create-line [10 20] [6 4])))
    (is (= [[10 20] [9 14]] (create-line [10 20] [-1 -6])))))

(deftest test-movement-vector
  (testing "Creates a movement vector from a wiring command"
    (is (= [0 0] (to-movement-vector "U0")))
    (is (= [0 -10] (to-movement-vector "U10")))
    (is (= [0 6] (to-movement-vector "D6")))
    (is (= [10 0] (to-movement-vector "R10")))
    (is (= [-5 0] (to-movement-vector "L5")))))

(deftest test-parse-wire-commands
  (testing "Wire command string is turned into array of movement vectors."))