1.本模块主要功能是用于发布karaf的feature
2.在karaf 3.0.0版本中执行如下命令
feature:repo-add mvn:com.daren.core/karaf-features/1.0/xml/features
feature:repo-add mvn:org.apache.cxf.karaf/apache-cxf/2.7.10/xml/features
feature:repo-add mvn:org.apache.shiro/shiro-features/1.2.3/xml/features
部署全部：
feature:install iris-all
部署运行环境：
feature:install webconsole openjpa pax-wicket couchdb
install wrap:mvn:org.wicketstuff/wicketstuff-shiro/6.14.0
install wrap:mvn:org.apache.velocity/velocity/1.6.3
install wrap:mvn:org.freemarker/freemarker/2.3.19
install wrap:mvn:org.antlr/stringtemplate/4.0.2