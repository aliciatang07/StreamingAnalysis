##Tech Stack 
Kafka for streaming data processing <br/>
MongoDB for database <br/>
Spark for map reduce computation<br/>
web frame(last part)<br/>
realtime api for feeding streaming data <br/>
django for backend server <br/>


possible: kubernetes, docker <br/>


## basic idea:
1. music recommendation system(based on categorization, author, style) <br/>
2. a generalization recommendation tool(client can feed own data and recommendation algorithm)ex: api <br/>
3. good extension opportunity for further expansion 
4.tryout different ML model for recommendation system 
5.backend side: user type their favoriate music and provide their
music listening preference


##basic architecture
separate data processing part, that part provide separate api 
so backend can call on it and has its own logic 



## env setup 
local setup <br/>
aws/azure setup <br/>


## develop timeline 

- [x] 6.11-6.14 search resources , plan logic workflow necessary tech stack and framework
- [x] 6.14-6.21 setup real-time api and feed into kafka and connect to spark streaming
- [x] 6.28 -7.6 完成clustering streaming 然后存db 考虑要不要引入elastic search
- [ ] 7.6 -7.13 experiment topic partition 和算法的运用(aim for a small report or blog) （optional:写monitoring tool（spark,kafka performance visualization)
- [ ] 7.13 - 8.1  做generalization 把整个pipeline 提成出几个接口

1. data source api(assume data comes from fetching api periodically)
2. streaming algorithm(a simple, no need to train model) ：这个接口感觉比较难
3. what to save in db 
4. system configurtion: kafka partition topic number, spark 部署方式 mesos yarn standalone local 
5. search part 
- [ ]8.1 - 后续 to cloud 





- [ ] 6.21-6.28 main logic of recommendation algorithm 
- [ ] 6.29-7.6  setup database and relevant query 
simple monitoring tool for system performance 
- [ ] 7.6 -7.13 connect to backend server and basic logic 
- [ ] 7.13-7.20 local test and deploy to cloud (try different cluster operation like yarm and cluster for spark)
- [ ] 7.20-7.27 frontend 
- [ ] 7.27 -8.3 generalize function and testing 
- [ ] consider incorporate 
- [ ] simple UI for showing system performance 


## practice goal 
streaming process, big data  <br/>
distributed system <br/>
cloud computing <br/>
concurrency? parallel <br/>


## focus resut
system performance <br/>
prediction result(accuracy)<br/>


## temp resource

spotify real-time api 
https://developer.spotify.com/documentation/web-api/reference-beta/    <br/>

audio analysis https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-analysis/