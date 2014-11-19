# Storm + Kafka in Clojure

This is a working implementation skeleton for a basic storm application with a Kafka producer in Clojure. It uses the lastest versions for both Kafka and Apache.

The producer will connect to the Twitter Streaming API and put the tweets into the system.
The Storm Spout will take those messages.

## Project Setup

First you will need Kafka **[(kafka_2.9.2-0.8)]** and storm **[(apache-storm-0.9.2-incubating)]** from the apache site.

### 1. Configure Zookeeper
Edit *zookeper.properties* (inside kafka/config) and ensure that:
> dataDir=/tmp/zookeeper

To start Zookeeper:
> zookeeper-server-start.sh config/zookeeper.properties

### 3. Configure Kafka Broker
Edit *server.properties* to ensure you have a unique *broker.id* (just in case you are starting more than one broker), a valid *log dir* and **zookeeper.connect** is on the same port as your *zookeeper.properties* (localhost:2181).

To start kafka broker:
> kafka-server-start.sh config/server.properties

### 4. Topics

I am not sure but Kafka shoul create it whenever a message is sent, but you cancreate it manually:

> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myFirstTopic

You can check your topic list by:
> bin/kafka-topics.sh --list --zookeeper localhost:2181

## Project conf file
To run both the producer and the Storm topology you must provide a route to a config file (same file). This file should have the following structure:

```properties
 # Twitter credentials
consumerKey = MyTwConsumerKey
consumerSecret = MyTwConsumerSecret
accessToken = 888888888-MyAccessToken
accessTokenSecret = MyTwAccessTokenSecret

#proxy (just in case)
proxyHost=proxyhost
proxyPort=8080

#producer
# Kafka conf (default port is 9092)
broker.list = localhost:9092
serializer.class=kafka.serializer.StringEncoder
request.required.acks=1
kafka.topic=MyFirstTopic

#Location in ZK for the Kafka spout to store state.
kafka.zkRoot=/tweet_event_sprout
kafka.zkBrokepath=/brokers

consumerGroupId=StormSpout

# default port
zookeeper=localhost:2181

#topology
topologyName=my-topology

```


## Deploying



[(kafka_2.9.2-0.8)]:http://kafka.apache.org/downloads.html
[(apache-storm-0.9.2-incubating)]:https://storm.apache.org/downloads.html

