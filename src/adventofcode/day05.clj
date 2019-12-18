(ns adventofcode.day05)

(require '[clojure.string :as str])

(def OP_ADD 1)
(def OP_MULTIPLY 2)
(def OP_INPUT 3)
(def OP_OUTPUT 4)
(def OP_EXIT 99)
(def PROGRAM_STEP 4)

(def INPUT
  "Input value, which we provide to the only input operation in the TEST program"
  1)


(defn read-program
  "Returns the parsed program"
  []
  (let [program (slurp "day5.txt")]
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

(declare execute-op-input
         execute-op-output)

(defn run-program
  "Recursively execute the program, returning the final program 
   as soon as the exit code 99 is reached."
  ([program input] (run-program program 0 input []))
  ([program pointer input output]
   (let [opcode (get-opcode program pointer)
         next-pointer (calculate-next-pointer pointer)]
     (condp = opcode
       OP_ADD  (run-program (add program pointer) next-pointer)
       OP_MULTIPLY (run-program (multiply program pointer) next-pointer)
       OP_INPUT (execute-op-input program pointer input output)
       OP_OUTPUT (execute-op-output program pointer input output)
       OP_EXIT output))))

(defn execute-op-input
  [program pointer input output]
  (run-program (do-input program pointer (first input)) next-pointer (rest input) output))

(defn execute-op-output
  [program pointer input output]
  (run-program program next-pointer input (do-output program pointer output)))

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

(defn day5-part1
  "Takes input and program, and returns the output values of the TEST program."
  [input program]
  (run-program (read-program)))