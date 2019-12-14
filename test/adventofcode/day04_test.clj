(ns adventofcode.day04-test
  (:require [clojure.test :refer :all]
            [adventofcode.day04 :refer :all]))

(deftest test-has-adjacent-digits
  (testing "Does have adjacent digits that are the same"
    (is (true? (has-adjacent-digits? 11)))
    (is (true? (has-adjacent-digits? 122)))
    (is (true? (has-adjacent-digits? 121445)))
    (is (true? (has-adjacent-digits? 1111111))))

  (testing "Does have adjacent digits of three which are the same"
    (is (true? (has-adjacent-digits? 3 111)))
    (is (true? (has-adjacent-digits? 3 1222)))
    (is (true? (has-adjacent-digits? 3 1214445)))
    (is (true? (has-adjacent-digits? 3 1111111))))

  (testing "Does not have adjacent digits that are the same"
    (is (not (true? (has-adjacent-digits? 10))))
    (is (not (true? (has-adjacent-digits? 10101010))))
    (is (not (true? (has-adjacent-digits? 123456)))))

  (testing "Does not have adjacent digits of three which are the same"
    (is (not (true? (has-adjacent-digits? 3 11))))
    (is (not (true? (has-adjacent-digits? 3 122))))
    (is (not (true? (has-adjacent-digits? 3 1214456))))
    (is (not (true? (has-adjacent-digits? 3 112113344))))))

(deftest test-monotonical-non-decreasing-digits
  (testing "All digits are monotonical non decreasing"
    (is (true? (monotononical-non-decreasing-digits? 11)))
    (is (true? (monotononical-non-decreasing-digits? 111)))
    (is (true? (monotononical-non-decreasing-digits? 12345)))
    (is (true? (monotononical-non-decreasing-digits? 1122333444455555))))

  (testing "Some digits decrease"
    (is (not (true? (monotononical-non-decreasing-digits? 10))))
    (is (not (true? (monotononical-non-decreasing-digits? 121))))
    (is (not (true? (monotononical-non-decreasing-digits? 1234345))))))

(deftest test-split-number-into-digits
  (testing "Splits number into collections of digits"
    (is (= [1] (split-number-into-digits 1)))
    (is (= [1 1] (split-number-into-digits 11)))
    (is (= [1 2 3 4 5] (split-number-into-digits 12345)))))

(deftest test-has-doubles
  (testing "Number has doubles"
    (is (true? (has-doubles? 11)))
    (is (true? (has-doubles? 1221)))
    (is (true? (has-doubles? 122211))))

  (testing "Triples don't count as doubles"
    (is (not (true? (has-doubles? 111))))
    (is (not (true? (has-doubles? 111222))))
    (is (not (true? (has-doubles? 12333))))))