(ns adventofcode.day05)

(require '[clojure.string :as str])

(def OP_ADD 1)
(def OP_MULTIPLY 2)
(def OP_INPUT 3)
(def OP_OUTPUT 4)
(def OP_EXIT 99)
(def PROGRAM_STEP 4)

(def PARAM_MODE_IMMEDIATE 1)
(def PARAM_MODE_ADDRESS PARAM_MODE_IMMEDIATE)
(def PARAM_MODE_POSITION 0)


(def INPUT
  "Input value, which we provide to the only input operation in the TEST program"
  [1])

(defn split-number-into-digits
  "Converts the given number into a sequence of its digits."
  [number] (map #(Character/digit % 10) (str number)))

(defn read-program
  "Returns the parsed program"
  []
  (let [program (slurp "day5.txt")]
    (->> (str/split program #",")
         (mapv #(Integer/parseInt %1)))))

(defn second-last
  "Returns the second but last item in coll in linear time."
  [coll] (last (butlast coll)))

(defn get-param
  [param-pos program pointer param-mode]
  (condp some [param-mode]
    #{PARAM_MODE_ADDRESS PARAM_MODE_IMMEDIATE} (nth program (+ pointer param-pos 1))
    #{PARAM_MODE_POSITION} (let [address (nth program (+ pointer param-pos 1))]
                             (nth program address))))
(defn parse-parameters
  [program pointer parameter-modes operation]
  (condp some [operation]
    #{OP_ADD OP_MULTIPLY} [(get-param 0 program pointer (last parameter-modes))
                           (get-param 1 program pointer (second-last parameter-modes))
                           (get-param 2 program pointer PARAM_MODE_ADDRESS)]

; WHAT SHOULD WE DO HERE? Is this line correct?
    #{OP_INPUT OP_OUTPUT} [(get-param 0 program pointer (last parameter-modes))])

(defn parse-operation
  "Reads the opcode and resolved parameters from the program at the given pointer."
  [program pointer]
  (let [opcode-vector (split-number-into-digits (nth program pointer))
        operation (clojure.string/join (take-last 2 opcode-vector))
        parameter-modes (take (- (count opcode-vector) 2) opcode-vector)
        parameters (parse-parameters program
                                     pointer
                                     parameter-modes
                                     operation)]
    [operation parameters]))


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
  [pointer opcode]
  (let [step-size (condp some [opcode]
                    #{OP_ADD OP_MULTIPLY} 4
                    #{OP_INPUT OP_OUTPUT} 2)]
    (+ pointer step-size)))

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
   (let [[opcode parameters] (parse-operation program pointer)
         next-pointer (calculate-next-pointer pointer opcode)]
     (condp = opcode
       OP_ADD  (run-program (add program pointer) next-pointer)
       OP_MULTIPLY (run-program (multiply program pointer) next-pointer)
       OP_INPUT (execute-op-input program pointer input output)
       OP_OUTPUT (execute-op-output program pointer input output)
       OP_EXIT output))))

(defn do-input [program pointer input-value])
(defn do-output [program pointer output])

(defn execute-op-input
  [program pointer input output]
  ; (run-program (do-input program pointer (first input)) next-pointer (rest input) output))
  ())

(defn execute-op-output
  [program pointer input output]
  ; (run-program program next-pointer input (do-output program pointer output)))
  ())

(defn day5-part1
  "Takes input and program, and returns the output values of the TEST program."
  []
  (run-program (read-program) INPUT))