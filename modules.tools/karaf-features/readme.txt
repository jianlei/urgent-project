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

karaf@root()> feature:repo-add mvn:org.apache.karaf.cellar/apache-karaf-cellar/3.0.0/xml/features
karaf@root()> feature:install -v cellar

karaf@root()> feature:repo-add mvn:org.apache.karaf.cave/apache-karaf-cave/3.0.0/xml/features
karaf@root()> feature:install cave-server


安装开发环境
1.删除karaf目录下的data目录,然后删除deploy目录下的全部jar包
2.重启karaf后，命令行输入feature:repo-add mvn:com.daren.tools/karaf-features/1.0/xml/features
3.输入feature:install -v urgent-dev，此时开发环境以及安装成功
4.在idea环境下运行install，如果看到下面的画面，就说明安装成功！
********************************************************************
*** WARNING: Wicket is running in DEVELOPMENT mode.              ***
***                               ^^^^^^^^^^^                    ***
*** Do NOT deploy to your live server(s) without changing this.  ***
*** See Application#getConfigurationType() for more information. ***
********************************************************************
