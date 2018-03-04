(defn constant [value] (fn [xyz] value))

(defn variable [name] (fn [xyz] (xyz name)))

(defn operation [func]
  (fn [& args]
    (fn [xyz]
      (apply func (map (fn [a] (a xyz)) args))
      )
    )
  )

(def add (operation +))

(def subtract (operation -))

(def multiply (operation *))

(def divide (operation (fn [a b] (/ (double a) (double b)))))

(def negate (operation (fn [a] (- a))))

(def sinh (operation (fn [a] (Math/sinh a))))

(def cosh (operation (fn [a] (Math/cosh a))))

(def operations {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'sinh sinh, 'cosh cosh})

(defn parseFunction [expression]
  (do
    (defn parse [str]
      (cond
        (list? str) (apply (operations (first str)) (map parse (rest str)))
        (number? str) (constant str)
        :else (variable (name str))))
    )
  (parse (read-string expression)))