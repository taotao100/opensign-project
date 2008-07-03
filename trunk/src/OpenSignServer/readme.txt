
*) CREATION

   mvn compile
   
   
*) TESTING

   mvn test   
  
   
*) GENERATING PACKAGE

  mvn package   
  

*) EXECUTING THE PROJECT

	 ==ECLIPSE==
	 
	 1.) Generate porject:
	 
	 		 mvn eclipse:eclipse 
	 		 
	 2.) Import project into eclipse
   
   3.) Executing:
       - To start the server execute class OSSMain
       - To run the JUNIT test classes execute class TestAll
   
   ==PACKAGE==
   
   1.) Unpack tar.gz archiev
   
   2.) Exectuing:
       - Run command: java -jar OpenSignServer-1.0-SNAPSHOT.jar
   
*) GENERATING THE tar.gz archieve

   mvn assebmly:assembly

*) NOTES
   
   Creation of project structure:
   
   mvn archetype:create -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=org.owasp.oss -DartifactId=OpenSignServer
   
   mvn -Declipse.workspace=D:\projects\owasp\repository\src