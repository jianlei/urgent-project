urgent-project
============
1.本项目使用karaf作为部署环境 <br/>
2.使用openjpa作为持久化数据  <br/>
3.使用wicket作为web开发框架  <br/>
4.部分模块使用了couchdb  <br/>
5.使用cxf发布restful服务（salesman模块的ISalesmanBeanService 实现了销售顾问登陆的restful服务） <br/>
6.使用pax wicket集成wicket到osgi <br/>
7.使用dbunit作为数据库的初始化和导出工具（under util bundle） <br/>
8.使用shiro作为安全框架 <br/>
9.使用bootstrap作为表现层框架 <br/>
10.core-archetype用于新建业务模块
（例如：创建一个order业务模块
 mvn archetype:generate -DgroupId=com.daren.order <br/>
 -DartifactId=order -Dversion=1.0-SNAPSHOT -Dpackage=com.daren.order <br/>
 -DarchetypeGroupId=com.daren.core -DarchetypeArtifactId=com.daren.core.archetype <br/>
 -DarchetypeVersion=1.0-SNAPSHOT -DarchetypeRepository=local）<br/>
11.使用acitviti作为工作流引擎  <br/>
12.core-util的CouchdbConfig實現了osgi的configAdmin（blueprint） <br/>
13.mvn install:install-file -Dfile=d:\kaptcha-2.3.2.jar -DgroupId=com.google.code -DartifactId=kaptcha -Dversion=2.3.2 -Dpackaging=jar
14.osgi service事务控制，在blueprint里的bean定义中加入<tx:transaction method="*" value="Required"/>
15.安装redis为window服务：redis-server --service-install redis.windows.conf