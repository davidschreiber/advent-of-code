(ns adventofcode.day02-test
  (:require [clojure.test :refer :all]
            [adventofcode.day02 :refer :all]))

(deftest test-get-opcode
  (testing "get-opcode"
    (testing "returns opcode stored at instruction pointer"
      (is (= 1 (get-opcode [1,2,3,4] 0)))
      (is (= 2 (get-opcode [1,2,3,4] 1)))
      (is (= 3 (get-opcode [1,2,3,4] 2)))
      (is (= 4 (get-opcode [1,2,3,4] 3))))))

(deftest test-calculate-next-pointer
  (testing "next-pointer increments pointer by 4"
    (is (= 4 (calculate-next-pointer 0)))
    (is (= 8 (calculate-next-pointer 4)))))

(deftest test-output-pos
  (testing "output-pos"
    (testing "returns the forth value of an intcode"
      (is (= 4 (output-pos [1,2,3,4] 0)))
      (is (= 2 (output-pos [3,2,1,2] 0)))
      (is (= 6 (output-pos [4,6,2,6] 0))))

    ; The output position is always the value at offset 3 starting at the pointer
    (testing "considers the intcode pointer"
      (is (= 1 (output-pos [1,2,3,4,1,2,3] 1)))
      (is (= 4 (output-pos [1,2,3,4,1,4,0] 2)))
      (is (= 9 (output-pos [1,2,3,4,1,8,9] 3))))))

(deftest test-get-input-value-at
  (testing "get-input-value-at returns the value of the field denoted by the index"
    (is (= 10 (get-input-value-at [1,4,5,4,10,20,30] 0 1)))
    (is (= 20 (get-input-value-at [1,4,5,4,10,20,30] 0 2)))
    (is (= 2  (get-input-value-at [1,4,5,4,2,4,5,1] 4 1)))
    (is (= 4  (get-input-value-at [1,4,5,4,2,4,5,1] 4 2)))))

(deftest test-run-program
  (testing "Running the program with pre-defined values from the instructions."
    (is (= [2 0 0 0 99] (run-program [1,0,0,0,99])))
    (is (= [2 3 0 6 99] (run-program [2,3,0,3,99])))
    (is (= [2 4 4 5 99 9801] (run-program [2,4,4,5,99,0])))
    (is (= [30 1 1 4 2 5 6 0 99] (run-program [1,1,1,4,99,5,6,0,99])))))

(deftest test-restore-1202-program-alarm-state
  (testing "Fixes 1202 program seed state"
    (is (= [1,12,2,0,1,2,3,4] (restore-1202-program-alarm-state [1,2,3,0,1,2,3,4])))
    (is (= [99,12,2,1] (restore-1202-program-alarm-state [99,1,1,1])))))

(deftest test-prepare-program-seeds
  (testing "prepare-program-seeds injects given seed values into program"
    (is (= [1,2,3] (prepare-program-seeds [1,0,0] 2 3)))
    (is (= [1,2,3,0,0] (prepare-program-seeds [1,0,0,0,0] 2 3)))
    (is (= [1,99,0,0,0,99] (prepare-program-seeds [1,0,0,0,0,99] 99 0)))))

(deftest test-find-program-seeds
  (testing "Checks that find-program-seeds produces the originally used 1202 code."
    (is (= [12, 2] (find-program-seeds (read-program) 3409710)))))