#!/bin/bash

#spark-submit --class com.RecSystem.DirectKafkaWordCount ./target/scala-2.11/RecSystem-assembly-1.0.jar localhost:9092 consumer1 word1,word2
#spark-submit --class com.RecSystem.DirectKafkaWordCount ./target/scala-2.11/RecSystem-assembly-1.0.jar localhost:9092 consumer1 train1 test1

spark-submit --class com.RecSystem.KafkaClustering --master spark://localhost:7077 ./target/scala-2.11/RecSystem-assembly-1.0.jar $1 $2 $3 $4 $5 $6 $7
