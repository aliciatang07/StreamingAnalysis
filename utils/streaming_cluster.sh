spark-submit --class com.StreamingAnalysis.StreamingClustering --master spark://localhost:7077 ./target/scala-2.11/StreamingAnalysis-assembly-1.0.jar $1 $2 $3
#<trainingDir> <testDir> <batchDuration> <numClusters> <numDimensions>