自动创建模块的maven archtype.
how to use:
mvn archetype:generate -DarchetypeCatalog=local

例如：创建一个order业务模块
mvn archetype:generate -DgroupId=com.daren.order -DartifactId=order -Dversion=1.0-SNAPSHOT -Dpackage=com.daren.order -DarchetypeGroupId=com.daren.core -DarchetypeArtifactId=com.daren.core.archetype -DarchetypeVersion=1.0-SNAPSHOT -DarchetypeRepository=local