if [ "$1" == "background" ];then
  if [ "$2" == "start" ];then
  brew services $2 zookeeper
  brew services $2 kafka
  else
  brew services $2 kafka
  brew services $2 zookeeper
  fi
else
  brew services start zookeeper
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
fi