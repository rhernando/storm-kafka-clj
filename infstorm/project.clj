(defproject infstorm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 ;[clojurewerkz/propertied "1.2.0"]
                 [cheshire "5.3.1"]
                 [org.clojure/data.json "0.2.5"]
                 ;[storm/storm-kafka "0.9.0-wip16a-scala292"]
                 [org.apache.storm/storm-kafka "0.9.2-incubating"]
                 ;[com.netflix.curator/curator-framework "1.0.8"]
                 ]
  :repositories {"project" "http://guru.informa.es/maven2"}
  :aot [infstorm.TopologySubmitter]
  ;; include storm dependency only in dev because production storm cluster provides it
  :profiles {:dev {:dependencies [
                                  [org.apache.storm/storm-core "0.9.2-incubating"]
                                  [org.apache.kafka/kafka_2.9.2 "0.8.1.1" :exclusions [[javax.jms/jms] [com.sun.jdmk/jmxtools] [com.sun.jmx/jmxri]  [org.apache.zookeeper/zookeeper] ]]
                                  ]}})
