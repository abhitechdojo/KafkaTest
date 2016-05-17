# Example to publish and subscribe messages into Kafka

## clone the spotify/docker image

git clone https://github.com/spotify/docker-kafka.git

Edit the docker file to put the latest version of docker in the file

now build the file using `docker built -t kafka_9.0 .`

## Run the Docker container

docker run --rm -p 2181:2181 -p 9092:9092 -p 8081:8081 --env ADVERTISED_HOST=\`docker-machine ip \\`docker-machine active\\`` --env ADVERTISED_PORT=9092 -v /Users/abhishek.srivastava/MyProjects/KafkaTest/target/scala-2.11:/app -it --name kafka kafka_9.0 bash

## start the zookeeper service

sh /opt/kafka_2.11-0.9.0.1/bin/zookeeper-server-start.sh /opt/kafka_2.11-0.9.0.1/config/zookeeper.properties > ~/zookeeper.out 2> ~/zookeeper.out &

## start the kafka service

sh /opt/kafka_2.11-0.9.0.1/bin/kafka-server-start.sh /opt/kafka_2.11-0.9.0.1/config/server.properties > ~/kafka.out 2> ~/kafka.out &

## create a topic

/opt/kafka_2.11-0.9.0.1/bin/kafka-topics.sh --zookeeper localhost:2181 --create --partitions 1 --replication-factor 1 --topic test

## list topics

/opt/kafka_2.11-0.9.0.1/bin/kafka-topics.sh --zookeeper localhost:2181 --list

## test the consumer

opt/kafka_2.11-0.9.0.1/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test

## test the producer

/opt/kafka_2.11-0.9.0.1/bin/kafka-console-producer.sh --broker-list localhost:9092 -topic test
