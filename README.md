cayenne-osgi-example
====================

A simple OSGi bundle that shows how to use [Apache Cayenne](http://cayenne.apache.org/) under OSGi.

_Note that this is still work in progress, until [CAY-1882](https://issues.apache.org/jira/browse/CAY-1882) is fixed._

Prerequisites
-------------

* Cayenne 3.2M2 or newer. 
* An OSGi container. (All testing for this example was done with [Apache Felix](http://felix.apache.org/)  4.2.1)

Configuring OSGi Container
--------------------------

Install third-party dependencies, which are somewhat different from normal Cayenne, as we need to use OSGi-friendly analogs. You can get all these from Maven Central. I am showing the file paths after they are already installed in my local Maven repo (this way you can see artifact names and versions used) :

    install file:/Users/cayenne/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.5/jcl-over-slf4j-1.7.5.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/slf4j-simple/1.7.5/slf4j-simple-1.7.5.jar
    install file:/Users/cayenne/.m2/repository//org/apache/servicemix/bundles/org.apache.servicemix.bundles.velocity/1.7_6/org.apache.servicemix.bundles.velocity-1.7_6.jar
    install file:/Users/cayenne/.m2/repository/commons-lang/commons-lang/2.4/commons-lang-2.4.jar
    install file:/Users/cayenne/.m2/repository/org/apache/servicemix/bundles/org.apache.servicemix.bundles.derby/10.10.1.1_1/org.apache.servicemix.bundles.derby-10.10.1.1_1.jar

Install 2 Cayenne bundles:

    install file:/Users/cayenne/work/cayenne/cayenne-di/target/cayenne-di-3.2.M2-SNAPSHOT.jar
    install file:/Users/cayenne/work/cayenne/cayenne-server/target/cayenne-server-3.2.M2-SNAPSHOT.jar

Running the Example
-------------------

Install the bundle

    install  file:/Users/cayenne/work/cayenne-osgi-example/target/cayenne-osgi-example-1.0-SNAPSHOT.jar



