

*) CREATION

   mvn compile
   
   
*) TESTING

   mvn test   
  
   
*) GENERATING PACKAGE

  mvn package   
  

*) EXECUTING PACKAGE
   
   Once project is inegrated in eclipse, execute class OSSMain.
   
   TODO: create mavnen target
   
   Not working any more due to dependencies:
   Place BouncyCasle (bcprov-jdk16-136.jar) in the same directory as the server and execute:
   java -jar OpenSignServer-1.0-SNAPSHOT.jar   
   

*) NOTES
   
   Creation of project structure:
   
   mvn archetype:create -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=org.owasp.oss -DartifactId=OpenSignServer
   
   mvn -Declipse.workspace=D:\projects\owasp\repository\src