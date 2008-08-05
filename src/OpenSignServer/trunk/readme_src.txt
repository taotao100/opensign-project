*******************************************************************************
*                   OPEN SIGN SERVER - SETTING UP PROJECT                     *
*******************************************************************************

This file elaborates the necessary steps to build the project from it's sources.


== Java ==

	Download and install JDK 1.6


== Maven ==

	Download and install Maven2. Set the M2_HOME, M2 and JAVA_HOME environment variable as pointed out at: http://maven.apache.org/download.html.
	
	Install Bouncy Castle version 140:
	
	mvn install:install-file -DgroupId=bouncycastle -DartifactId=bcprov-jdk16 -Dversion=140 -Dpackaging=jar -Dfile=bcprov-jdk16-140.jar
	
	Create file .m2\repository\bouncycastle\bcprov-jdk16\140\bcprov-jdk16-140.pom in Maven repository with following content:
	
	
	<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

	<modelVersion>4.0.0</modelVersion>
	<groupId>bouncycastle</groupId>
	<artifactId>bcprov-jdk16</artifactId>
	<version>140</version>
	<packaging>jar</packaging>
	<name>Legion of the Bouncy Castle Java Cryptography APIs</name>
	<description>The Bouncy Castle Crypto package is a Java implementation of cryptographic algorithms. The package is organised so that it contains a light-weight API suitable for use in any environment (including the newly released J2ME) with the additional infrastructure to conform the algorithms to the JCE framework.</description>
	<url>http://www.bouncycastle.org/java.html</url>

	<licenses>
		<license>
			<name>Bouncy Castle License</name>
			<url>http://www.bouncycastle.org/licence.html</url>
			<distribution>repo</distribution>
		</license>
	</licenses>

</project>

== Database ==
	
	If intending to use a data base one will need to download and install MYSQL data base and set the storage type to MYSQL in file config.properties.
	Make sure the MYSQL database has a account with the user name 'root' and empty password, which is the default configuration of MYSQL anyway.
	

	The project will now compile from command line (see section Maven Targets).
	
	
== Eclipse ==

	Window -> Preferences -> Java -> Installed JREs:
	Select: Java Runtime Environment/ or Java Development Kit 1.6 
	
	Window -> Preferences -> Java -> Compiler:
	Select: Compiler compliance level: 1.6
	
	Install Maven2 Plugin:
	Help -> Software updates ... -> Add site: 
	http://m2eclipse.sonatype.org/update/
	
	Import OpenSignServer as Maven project as Maven project:
	File -> Import ... -> General -> Maven projects -> ...
	
	Maven Targets (see following section) may now be executed from eclipse by a 
	right mouse click on pom.xml or by setting up External Tools.
	
	To execute the server application, simply run org.owasp.oss.OSSMain as a Java 
	application. 


== Maven Targets ==

	o Compilation: 
		mvn compile   
    o Running JUnit test cases: 
    	mvn test
    o Creating package tar.gz:  
  		mvn package
  	o Javadoc:
  		mvn javadoc:javadoc
  				
   
 