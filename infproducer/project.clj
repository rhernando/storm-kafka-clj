(defproject infproducer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [clojurewerkz/propertied "1.2.0"]
                 [clj-kafka "0.2.6-0.8"]
                 [org.twitter4j/twitter4j-core "3.0.5"]
                 [org.twitter4j/twitter4j-stream "3.0.5"]
                 ]
  :main infproducer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
