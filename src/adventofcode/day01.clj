; Day 1, part 1
(defn get-fuel-requirement 
  "Calculates how much fuel is required for transportation of the given mass."
  [mass]
  (int (- (Math/floor (/ mass 3)) 2)))

(defn module-fuel 
  "Calculates the f"
  [mass]
  (let [fuel (get-fuel-requirement mass)]
    (if (<= fuel 0)
      0
      (+ fuel (module-fuel fuel)))))

(defn calculate_total_fuel []
   (with-open [reader (clojure.java.io/reader "day1.txt")]
     (->> (line-seq reader)
          (map #(Integer/parseInt %1))
          (map module-fuel)
          (reduce + 0)
          (int))))

(calculate_total_fuel)

; Day 1, part 2

