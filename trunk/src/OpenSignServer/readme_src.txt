
*) CREATION

   mvn compile
   
   
*) TESTING

   mvn test   
  
   
*) GENERATING PACKAGE

   mvn package   
  

*) EXECUTING THE PROJECT

   ==ECLIPSE==
	 
	 1.) Generate project:
	 
	 		 mvn eclipse:eclipse 
	 		 
	 2.) Import project into eclipse
   
     3.) Executing:
       - To start the server execute class OSSMain
       - To run the JUNIT test classes execute class TestAll
   
   
*) GENERATING THE tar.gz archive

   mvn assebmly:assembly
   
*) GENERATING OF JAVADOC

   mvn javadoc:javadoc
      

*) NOTES
   
   Creation of project structure:
   
   mvn archetype:create -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=org.owasp.oss -DartifactId=OpenSignServer
   
   mvn -Declipse.workspace=D:\projects\owasp\repository\src