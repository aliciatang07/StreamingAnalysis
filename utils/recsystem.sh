#!/bin/bash

spark-submit --class com.StreamingAnalysis.RecSystem --master spark://localhost:7077 ./target/scala-2.11/StreamingAnalysis-assembly-1.0.jar


