; Day 2, part 1
(ns adventofcode.day02)

(require '[clojure.string :as str])

(def OP_ADD 1)
(def OP_MULTIPLY 2)
(def OP_EXIT 99)
(def PROGRAM_STEP 4)

(defn read-program []
  (let [program (slurp "day2.txt")]
    (->> (str/split program #",")
         (mapv #(Integer/parseInt %1)))))

(defn get-opcode
  "Reads the opcode from the program at the given pointer."
  [program pointer]
  (nth program pointer))

(defn get-input-value-at
  "Given a program and a program pointer, this methods resolves 
   the field which is addressed by the input value at index."
  [program pointer index]
  (nth program (nth program (+ pointer index))))

(defn output-pos
  [program pointer]
  (nth program (+ pointer 3)))

(defn calculate-next-pointer
  "Returns the next incode pointer which should be executed after the
   current pointer."
  [pointer]
  (+ pointer PROGRAM_STEP))

(defn execute-op-at
  "Takes a program, a frame pointer and an operation (+ or *) which
   should be performed in the current frame. Returns the resulting 
   program."
  [program pointer op]
  (assoc program
         (output-pos program pointer)
         (op (get-input-value-at program pointer 1)
             (get-input-value-at program pointer 2))))

(defn add [program pointer] (execute-op-at program pointer +))
(defn multiply [program pointer] (execute-op-at program pointer *))

(defn run-program
  "Recursively execute the program, returning the final program 
   as soon as the exit code 99 is reached."
  ([program] (run-program program 0))
  ([program pointer]
   (let [opcode (get-opcode program pointer)
         next-pointer (calculate-next-pointer pointer)]
     (case opcode
       1  (run-program (add program pointer) next-pointer)
       2  (run-program (multiply program pointer) next-pointer)
       99 program))))

(defn prepare-program-seeds
  [program seed1 seed2]
  (-> program
      (assoc 1 seed1)
      (assoc 2 seed2)))

(defn get-program-result [program] (first program))

; day 2; example 1
(defn restore-1202-program-alarm-state
  [program]
  (prepare-program-seeds program 12 2))

(defn load-and-run-program-with-seeds
  [seed1 seed2]
  (get-program-result
   (run-program
    (prepare-program-seeds
     (read-program) seed1 seed2))))

(defn day2-part1 []
  (get-program-result
   (run-program
    (restore-1202-program-alarm-state
     (read-program)))))

;(day2-part1)

; day 2; example 2

; Better approach than this: Create a lazy sequence of seed pairs, 
; iterate over the list, and stop with the result.
(defn find-program-seeds
  "Finds and returns necessary seeds for 'program' that procudes 'output'
   by trying different program seeds values. Seed values is a vector
   containing two values. Returns a vector with two values."
  [program desired-output]
  (loop [seed1 0 seed2 0]
    (let [program-output (load-and-run-program-with-seeds seed1 seed2)]
      (if (= desired-output program-output)
        [seed1, seed2]
        (if (< seed2 99)
          (recur seed1 (inc seed2))
          (if (< seed1 99)
            (recur (inc seed1) 0)
            (println "Did not find desired output.")))))))

(find-program-seeds (read-program) 19690720)