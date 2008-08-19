*******************************************************************************
*                   OPEN SIGN SERVER - SETTING UP PROJECT                     *
*******************************************************************************

This file elaborates the necessary steps to build the project from it's sources.


== Java ==

	Download and install JDK 1.6


== Maven ==

	Download and install Maven2. Set the M2_HOME, M2 and JAVA_HOME environment variable as pointed out at: http://maven.apache.org/download.html.

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
  				
   
 