*******************************************************************************
*                   OPEN SIGN SERVER - SETTING UP PROJECT                     *
*******************************************************************************

This file elaborates the necessary steps to build the project from it's sources.


== Java ==

	Download and install JDK 1.6


== Maven ==

	Download and install Maven2. Set the M2_HOME and M2 environment variable.
	The project will now compile from command line (see section Maven Targets).


== Eclipse ==

	Window -> Preferences -> Java -> Installed JREs:
	Select: Java runtime environment 1.6
	
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
   
   
== Notes ==      
   
   The initial project was created the following way:
   
   mvn archetype:create -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=org.owasp.oss -DartifactId=OpenSignServer
   
   mvn -Declipse.workspace=D:\projects\owasp\repository\src
 