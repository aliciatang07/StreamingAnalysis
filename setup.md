kafka location
/usr/local/Cellar/kafka/2.5.0: 186 files, 58.6MB
To have launchd start zookeeper now and restart at login:
  brew services start zookeeper
Or, if you don't want/need a background service you can just run:
  zkServer start
  To have launchd start kafka now and restart at login:
    brew services start kafka
  Or, if you don't want/need a background service you can just run:
 
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties



background mode:
```
brew services start zookeeperm 
brew services start kafka
brew services stop kafka
brew services stop zookeeper
```

non-background mode:
```
zkServer start
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties

```


history command 
```
  501  cd /usr/local/Cellar/kafka/2.5.0
  502  ls
  503  cd bin
  504  ./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic word1
  505  ls
  506  cd ..
  507  ls
  508  kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic word1
  509  kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic word2
  510  kafka-topics --list  --zookeeper localhost:2181
  511  kafka-console-producer --broker-list localhost:9092 --topic word1
  512  kafka-console-producer --broker-list localhost:9092 --topic word1
  513  kafka-console-producer --broker-list localhost:9092 --topic word1
       kafka-console-consumer --bootstrap-server localhost:9092 --topic music1 --from-beginning

```