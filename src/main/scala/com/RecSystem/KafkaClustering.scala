package com.RecSystem



import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.clustering.{KMeansModel, StreamingKMeans}
import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaClustering {
  def main(args: Array[String]): Unit = {
    if (args.length < 7) {
      System.err.println(s"""
                            |Usage: DirectKafkaWordCount <brokers> <groupId> <topics>
                            |  <brokers> is a list of one or more Kafka brokers
                            |  <groupId> is a consumer group name to consume from topics
                            |  <topics> is a list of one or more kafka topics to consume from
                            |
        """.stripMargin)
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()
    // ##ISSUE: how to pass k, dim from producer side (k can determine from consumer side, but dim has to be the
    // length of vectors)
    val Array(brokers, groupId, topics1, topics2, duration, k, dim) = args

    val sparkConf = new SparkConf().setMaster("local[4]").setAppName("KafkaClustering")
    val ssc = new StreamingContext(sparkConf, Seconds(duration.toInt))

    // Create direct kafka stream with brokers and topics
    val topicsSet1 = topics1.split(",").toSet
    val topicsSet2 = topics2.split(",").toSet

    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ConsumerConfig.GROUP_ID_CONFIG -> groupId,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer])
    //直接从kafka message来Kmeans clustering 分一个training和testing channel 避开textfilestream
    val trainmessages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet1, kafkaParams))

    val testmessages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet2, kafkaParams))


    val trainData = trainmessages.map(_.value)map(Vectors.parse)
    println("training data\n");
    //println(trainingData.collect());
        trainData.print();
    //    trainingData.foreach(rdd => rdd.foreach(elem => println(elem.my_attribute))
    val testData = testmessages.map(_.value).map(LabeledVector.parse)
    testData.print()

    val model = new StreamingKMeans()
      .setK(k.toInt)
      .setDecayFactor(1.0)
      .setRandomCenters(dim.toInt, 0.0)

    //TODO: further integration for loading saved models and saving result to mongodb
    //TODO: create multiple clustering model based on different characteristic(several group have strong associations maybe
    // like danceability and energy (need change on provide sider also) and consider how to abstract this
    // )

    //load the model if we have stored model and see how we can only load model in the first batch
//    val latestmodel = model.latestModel()
//    latestmodel.save(ssc.sparkContext, "target")
//    val model = KMeansModel.load("xxx")
    //TODO:BEFORE TRAINING WE NEED MAKE SURE VALUES ARE STANDARDIZED
    model.trainOn(trainData)
    println("starting the content\n");


    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
