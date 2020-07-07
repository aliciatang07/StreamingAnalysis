#!/bin/bash

spark-submit --class com.RecSystem.RecSystem --master spark://localhost:7077 ./target/scala-2.11/RecSystem-assembly-1.0.jar


