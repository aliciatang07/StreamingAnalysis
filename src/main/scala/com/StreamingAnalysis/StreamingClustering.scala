package com.StreamingAnalysis
import org.apache.spark.SparkConf
import org.apache.spark.mllib.clustering.StreamingKMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.streaming.{Seconds, StreamingContext}



//https://spark.apache.org/docs/latest/mllib-clustering.html#streaming-k-means
//https://arxiv.org/pdf/1203.2000.pdf

//*
//* Usage:
//*   StreamingKMeansExample <trainingDir> <testDir> <batchDuration> <numClusters> <numDimensions>
//*
//* To run on your local machine using the two directories `trainingDir` and `testDir`,
//* with updates every 5 seconds, 2 dimensions per data point, and 3 clusters, call:
//*    $ bin/run-example mllib.StreamingKMeansExample trainingDir testDir 5 3 2
//*

object StreamingClustering {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[4]").setAppName("StreamingKMeansExample")
    val ssc = new StreamingContext(conf, Seconds(args(0).toLong))

    val trainingData = ssc.textFileStream("file:///Users/Alicia/Desktop/RecSystem/data/train").map(Vectors.parse)
    println("training data\n");
    //println(trainingData.collect());
    trainingData.print();
//    trainingData.foreach(rdd => rdd.foreach(elem => println(elem.my_attribute))
    val testData = ssc.textFileStream("file:///Users/Alicia/Desktop/RecSystem/data/test").map(LabeledPoint.parse);
   testData.print();

    val model = new StreamingKMeans()
      .setK(args(1).toInt)
      .setDecayFactor(1.0)
      .setRandomCenters(args(2).toInt, 0.0)

//    val model = new StreamingKMeans().setK(3).setDecayFactor(1.0).setRandomCenters(2, 0.0)

    model.trainOn(trainingData)
    println("starting the content\n");
    val latestmodel = model.latestModel()
//    latestmodel.save(ssc.sparkContext, "target")
//    model.predictOnValues()

  // DSTREAM[Double,Int]
//    testData.map(lp=>println(lp.label))
   model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
//    val num = pairs.count()
//    println(s"starting the prediction = $num\n");
//    pairs.map(result=>println(result._1))
//    pairs.print()
//    pairs.saveAsTextFiles("data/result.txt")

    ssc.start()
    ssc.awaitTermination()
  }
}
