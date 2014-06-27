@ echo off
::auto install amdatu jar
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.mongo -Dversion=1.0.4 -Dpackaging=jar -Dfile=org.amdatu.mongo-1.0.4.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.scheduling.api -Dversion=1.0.0 -Dpackaging=jar -Dfile=org.amdatu.scheduling.api-1.0.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.scheduling.quartz -Dversion=1.0.1 -Dpackaging=jar -Dfile=org.amdatu.scheduling.quartz-1.0.1.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.search.api -Dversion=1.0.0 -Dpackaging=jar -Dfile=org.amdatu.search.api-1.0.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.search.solr.impl -Dversion=1.0.1 -Dpackaging=jar -Dfile=org.amdatu.search.solr.impl-1.0.1.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.security.tokenprovider.api -Dversion=1.0.0 -Dpackaging=jar -Dfile=org.amdatu.security.tokenprovider.api-1.0.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.security.tokenprovider.impl -Dversion=1.0.0 -Dpackaging=jar -Dfile=org.amdatu.security.tokenprovider.impl-1.0.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.security.tokenstorage.mongo -Dversion=1.0.1 -Dpackaging=jar -Dfile=org.amdatu.security.tokenstorage.mongo-1.0.1.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.resourcehandler -Dversion=1.0.4 -Dpackaging=jar -Dfile=org.amdatu.web.resourcehandler-1.0.4.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.rest.doc -Dversion=1.1.1 -Dpackaging=jar -Dfile=org.amdatu.web.rest.doc-1.1.1.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.rest.jaxrs -Dversion=1.0.0 -Dpackaging=jar -Dfile=org.amdatu.web.rest.jaxrs-1.0.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.rest.wink -Dversion=1.0.8 -Dpackaging=jar -Dfile=org.amdatu.web.rest.wink-1.0.8.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=net.vz.mongodb.jackson -DartifactId=mongo-jackson-mapper -Dversion=1.4.3 -Dpackaging=jar -Dfile=net.vz.mongodb.jackson.mongo-jackson-mapper-1.4.3.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.rest.doc.swagger.ui -Dversion=1.0.3 -Dpackaging=jar -Dfile=org.amdatu.web.rest.doc.swagger.ui-1.0.3.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=org.amdatu -DartifactId=org.amdatu.web.rest.doc.swagger -Dversion=1.0.4 -Dpackaging=jar -Dfile=org.amdatu.web.rest.doc.swagger-1.0.4.jar -DgeneratePom=true




