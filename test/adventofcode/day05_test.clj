(ns adventofcode.day05-test
  (:require [clojure.test :refer :all]
            [adventofcode.day05 :refer :all]))

(deftest test-parse-parameters
  (testing "Returns the resolved parameters in immediate mode"
    (is (= [2,3,4] (parse-parameters [1101,2,3,4,0] 0 [1,1] 1)))
    (is (= [3,4,5] (parse-parameters [1101,3,4,5,0] 0 [1,1] 1)))
    ))

(deftest test-parse-operation
    (testing "returns opcode stored at instruction pointer"
      (is (= [OP_ADD [3,4,4]] (parse-operation [0001,2,3,4,0] 0)))
      (is (= [OP_MULTIPLY [3,4,4]] (parse-operation [0002,2,3,4,0] 0)))
      ))

(deftest test-calculate-next-pointer
  (testing "next-pointer increments pointer by 4 for multiply and add operations"
    (is (= 4 (calculate-next-pointer 0 OP_ADD)))
    (is (= 4 (calculate-next-pointer 0 OP_MULTIPLY)))
    (is (= 8 (calculate-next-pointer 4 OP_MULTIPLY)))
    (is (= 12 (calculate-next-pointer 8 OP_MULTIPLY))))

  (testing "next-pointer increments by 2 for input and output operations"
    (is (= 2 (calculate-next-pointer 0 OP_INPUT)))
    (is (= 2 (calculate-next-pointer 0 OP_OUTPUT)))
    (is (= 4 (calculate-next-pointer 2 OP_OUTPUT)))
    (is (= 6 (calculate-next-pointer 4 OP_OUTPUT)))))

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

; (deftest test-run-program
;   (testing "Running the program with pre-defined values from the instructions."
;     (is (= [2 0 0 0 99] (run-program [1,0,0,0,99])))
;     (is (= [2 3 0 6 99] (run-program [2,3,0,3,99])))
;     (is (= [2 4 4 5 99 9801] (run-program [2,4,4,5,99,0])))
;     (is (= [30 1 1 4 2 5 6 0 99] (run-program [1,1,1,4,99,5,6,0,99])))))