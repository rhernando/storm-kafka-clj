(ns infstorm.topology
  "Topology

More info on the Clojure DSL here:

https://github.com/nathanmarz/storm/wiki/Clojure-DSL"
  (:require [infstorm
             ;[spouts :refer [type-spout]]
             [bolts :refer [stormy-bolt infstorm-bolt]]]
            [backtype.storm [clojure :refer [topology spout-spec bolt-spec]] [config :refer :all]]
            [clojure.java.io :as io]
            [clojurewerkz.propertied.properties :as p]
            )
  (:import [backtype.storm LocalCluster LocalDRPC]
           [backtype.storm.spout SchemeAsMultiScheme]
           [storm.kafka KafkaConfig KafkaSpout StringScheme SpoutConfig ZkHosts]
           ))

(defn kafka-spout
  [props]

  (let [
        spout-config (SpoutConfig. (new ZkHosts (:zookeeper props) (:kafka.zkBrokepath props)) (:kafka.topic props) (:kafka.zkRoot props) (:consumerGroupId props))
        ]
    (println "PRUEBA PRUEBA")
    (println  (:zookeeper props))
    (set! (. spout-config scheme) (SchemeAsMultiScheme. (StringScheme.) ) )
    ;(.forceStartOffsetTime spout-config -2)
    (KafkaSpout. spout-config)

    )

  )

(defn stormy-topology [props]
  (topology
   ;{"spout" (spout-spec type-spout)}
   {"twitter-spout" (spout-spec (kafka-spout props) :p 1)}

   {"infstorm-bolt" (bolt-spec {"twitter-spout" :shuffle} infstorm-bolt :p 2)}))

(defn run! [confpath & {debug "debug" workers "workers" :or {debug "true" workers "2"}}]
  (let [props (p/properties->map (p/load-from (io/file confpath)) true)]
        (println "PRUEBA PRUEBA")
    (println "PRUEBA PRUEBA")

    (doto (LocalCluster.)
    (.submitTopology "infstormy"
                     {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
                      TOPOLOGY-WORKERS (Integer/parseInt workers)}
                     (stormy-topology props)))
    )
  )
