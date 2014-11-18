(ns infstorm.TopologySubmitter
  (:require [infstorm.topology :refer [stormy-topology]]
            [backtype.storm [config :refer :all]]
            [clojure.java.io :as io]
            [clojurewerkz.propertied.properties :as p])
  (:import [backtype.storm StormSubmitter])
  (:gen-class))

(defn -main [confpath & {debug "debug" workers "workers" :or {debug "false" workers "4"}}]
  (let [props (p/properties->map (p/load-from (io/file confpath)) true)]
    (StormSubmitter/submitTopology
   (:topologyName props)
   {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
    TOPOLOGY-WORKERS (Integer/parseInt workers)}
   (stormy-topology props))
    )
  )
