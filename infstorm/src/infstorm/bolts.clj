(ns infstorm.bolts
  "Bolts.

More info on the Clojure DSL here:

https://github.com/nathanmarz/storm/wiki/Clojure-DSL"
  (:require [backtype.storm [clojure :refer [emit-bolt! defbolt ack! bolt]]]))

(defbolt stormy-bolt ["tweet"] [tuple collector]
  (let [words (.split (.getString tuple 0) " ")]
    (println "sadssssssssssssssssssssssssssssssssssssssssssssssss")
  ))


(defbolt infstorm-bolt ["message"] [{stormy :stormy :as tuple} collector]
  (emit-bolt! collector [(str "infstorm produced: "stormy)] :anchor tuple)
  (ack! collector tuple))
