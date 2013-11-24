cayenne-osgi-example
====================

A simple OSGi bundle that shows how to use [Apache Cayenne](http://cayenne.apache.org/) under OSGi.

Prerequisites
-------------

* Cayenne 3.2M2 or newer. 
* An OSGi container. (All testing for this example was done with [Apache Felix](http://felix.apache.org/)  4.2.1)

Configuring OSGi Container
--------------------------

Install a bunch of bundles. You can get all these from Maven Central. I am showing the file paths after they are already installed in my local Maven repo (this way you can see artifact names and versions used).

OSGi-friendly versions of third-party dependencies needed by Cayenne:

    install file:/Users/cayenne/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.5/jcl-over-slf4j-1.7.5.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar
    install file:/Users/cayenne/.m2/repository/org/slf4j/slf4j-simple/1.7.5/slf4j-simple-1.7.5.jar
    install file:/Users/cayenne/.m2/repository//org/apache/servicemix/bundles/org.apache.servicemix.bundles.velocity/1.7_6/org.apache.servicemix.bundles.velocity-1.7_6.jar
    install file:/Users/cayenne/.m2/repository/commons-lang/commons-lang/2.4/commons-lang-2.4.jar

2 Cayenne bundles (from local build, until we release 3.2.M2 officially):

    install file:/Users/cayenne/work/cayenne/cayenne-di/target/cayenne-di-3.2.M2-SNAPSHOT.jar
    install file:/Users/cayenne/work/cayenne/cayenne-server/target/cayenne-server-3.2.M2-SNAPSHOT.jar
    
H2 bundle used by the demo to run an in-memory DB:

      install file:/Users/cayenne/.m2/repository/com/h2database/h2/1.2.144/h2-1.2.144.jar

Finally, the demo app bundle:

      install  file:/Users/cayenne/work/cayenne-osgi-example/target/cayenne-osgi-example-1.0-SNAPSHOT.jar

Running the Example
-------------------

Check that everything is in place:

    g! lb
    START LEVEL 1
       ID|State      |Level|Name
        0|Active     |    0|System Bundle (4.2.1)
        1|Active     |    1|Apache Felix Bundle Repository (1.6.6)
        2|Active     |    1|Apache Felix Gogo Command (0.12.0)
        3|Active     |    1|Apache Felix Gogo Runtime (0.10.0)
        4|Active     |    1|Apache Felix Gogo Shell (0.10.0)
       33|Installed  |    1|Commons Collections (3.2.1)
       34|Installed  |    1|jcl-over-slf4j (1.7.5)
       35|Installed  |    1|slf4j-api (1.7.5)
       36|Installed  |    1|slf4j-simple (1.7.5)
       37|Installed  |    1|Apache ServiceMix :: Bundles :: velocity (1.7.0.6)
       38|Installed  |    1|Commons Lang (2.4.0)
       39|Installed  |    1|Cayenne Dependency Injection Container (3.2.0.M2-SNAPSHOT)
       40|Installed  |    1|Cayenne Server (3.2.0.M2-SNAPSHOT)
       41|Installed  |    1|H2 Database Engine (1.2.144)
       42|Installed  |    1|OSGi Cayenne Demo (1.0.0.SNAPSHOT)


Start the demo app bundle

    g! start 42
    Starting Cayenne OSGi example
    [Gogo shell] INFO org.apache.cayenne.configuration.XMLDataChannelDescriptorLoader - Loading XML configuration resource from bundle://30.0:1/cayenne-osgi-example.xml
    [Gogo shell] INFO org.apache.cayenne.configuration.XMLDataChannelDescriptorLoader - Loading XML DataMap resource from bundle://30.0:1/osgi-map.map.xml
    [Gogo shell] INFO org.apache.cayenne.configuration.XMLDataChannelDescriptorLoader - loading user name and password.
    [Gogo shell] INFO org.apache.cayenne.configuration.server.DataDomainProvider - setting DataNode 'h2db' as default, used by all unlinked DataMaps
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - Opening connection: jdbc:h2:mem:osgidb;MVCC=TRUE
    	Login: sa
    	Password: *******
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - +++ Connecting: SUCCESS.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - Detected and installed adapter: org.apache.cayenne.dba.h2.H2Adapter
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - --- transaction started.
    [Gogo shell] INFO org.apache.cayenne.access.dbsync.CreateIfNoSchemaStrategy - No schema detected, will create mapped tables
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - CREATE TABLE entity2 (amount INTEGER NULL, id INTEGER NOT NULL, PRIMARY KEY (id))
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - CREATE TABLE entity1 (id INTEGER NOT NULL, name VARCHAR(200) NULL, PRIMARY KEY (id))
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - CREATE TABLE AUTO_PK_SUPPORT (  TABLE_NAME CHAR(100) NOT NULL,  NEXT_ID BIGINT NOT NULL,  PRIMARY KEY(TABLE_NAME))
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - DELETE FROM AUTO_PK_SUPPORT WHERE TABLE_NAME IN ('entity1', 'entity2')
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('entity1', 200)
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('entity2', 200)
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - SELECT NEXT_ID FROM AUTO_PK_SUPPORT WHERE TABLE_NAME = 'entity1'
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - === returned 1 row. - took 16 ms.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - UPDATE AUTO_PK_SUPPORT SET NEXT_ID = NEXT_ID + 20 WHERE TABLE_NAME = 'entity1'
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - === updated 1 row.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - INSERT INTO entity1 (id, name) VALUES (?, ?)
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - [bind: 1->id:200, 2->name:'E1_1385305841827']
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - === updated 1 row.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - +++ transaction committed.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - --- transaction started.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - SELECT t0.name, t0.id FROM entity1 t0
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - === returned 1 row. - took 6 ms.
    [Gogo shell] INFO org.apache.cayenne.log.CommonsJdbcEventLogger - +++ transaction committed.
    g! 

Stop demo:

    g! stop 42
    Stopping Cayenne OSGi example

    


