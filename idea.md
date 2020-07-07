main algorithm 
要用流处理的思想 

existing ideas:
1. correlation analysis 
(danceability & energy ...)
2. genre analysis 
my ideas:
evaluating the performance of the new song in the specific genre, 
ranking, popularity, uniquness 
应该在data sender端加进一步的filter 根据genre send到不同的topic 然后接收端就可以
一个批次的data 一起处理 


1. =》 generalize： user端给出一个新的concept及算法 <br>
生成一个新的concept？ performance difficulty 产出频率
ex: boringness = loudness + tempo + (energy*100) + (danceability*100)
2. kmeans clustering<br>
https://en.wikipedia.org/wiki/Data_stream_clustering
3. streaming regression.<br>
https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/mllib/StreamingLogisticRegression.scala
https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/mllib/StreamingLinearRegressionExample.scala
问题:有些公式的生成或者魔性训练需要大量已存在数据  



4. sentiment analysis based on rhythm features 

further:
consider integrate distributed framework 