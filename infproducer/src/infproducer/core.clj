(ns infproducer.core
  (:require
   [clojurewerkz.propertied.properties :as p]
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   )
  (:use
   [clj-kafka.producer]
   [clj-kafka.zk]

   )
  (:import
   [twitter4j
    StatusListener TwitterStream TwitterStreamFactory
    FilterQuery]
   [twitter4j.conf ConfigurationBuilder Configuration]
   [twitter4j.json DataObjectFactory] )
  )

(defn tw-creds [props]
  (let [cb (ConfigurationBuilder.)]
    (.setOAuthConsumerKey cb (:consumerKey props))
    (.setOAuthConsumerSecret cb (:consumerSecret props))
    (.setOAuthAccessToken cb (:accessToken props))
    (.setOAuthAccessTokenSecret cb (:accessTokenSecret props))
    (.setHttpProxyHost cb (:proxyHost props))
    (.setHttpProxyPort cb (read-string (:proxyPort props)))


    ; Uncomment if you want access to the raw json
    (.setJSONStoreEnabled cb true)

    (.build cb)
    )
  )

(defn status-listener [props prod]
  "Implementation of twitter4j's StatusListener interface"
  (proxy [StatusListener] []

    (onStatus [^twitter4j.Status status]
      (println (.getText status))
      (send-message prod (message (:kafka.topic props) (DataObjectFactory/getRawJSON status)))

    )

    (onException [^java.lang.Exception e] (.printStackTrace e))
    (onDeletionNotice [^twitter4j.StatusDeletionNotice statusDeletionNotice] ())
    (onScrubGeo [userId upToStatusId] ())
    (onTrackLimitationNotice [numberOfLimitedStatuses] ())))


(defn get-twitter-stream-factory [props]
  (let [factory (TwitterStreamFactory. (tw-creds props))]
    (.getInstance factory)))


(defn do-sample-stream [props prod]
  (let [stream (get-twitter-stream-factory props)]
    (.addListener stream (status-listener props prod))
    (.sample stream)))



(defn initproducer
  [props]
  (producer {"metadata.broker.list" (:broker.list props)
             "serializer.class" (:serializer.class props)
             "partitioner.class" "kafka.producer.DefaultPartitioner"})
  )

(defn -main
  "I don't do a whole lot ... yet."
  [confpath & args]
  (println (brokers {"zookeeper.connect" "127.0.0.1:2181"}))

  (let [props (p/properties->map (p/load-from (io/file confpath)) true)
        prod (initproducer props)
       ]
    (do-sample-stream props prod)
    )

  )
