*******************************************************************************
*                      OPEN SIGN SERVER - VERSION 0.1                         *
*******************************************************************************

== Prerequisites ==

*) Install Java runtime 1.6


== Configuration ==

*) Configuration file: config.properties

*) Configuration of log method in file: log4j.properties
 

== Starting the Server ==

*) Windows: Click on run.bat

*) Linux: java -jar jar\OpenSignServer-0.1.jar

Server starts at: http://localhost:8080 ,whereas the port is configurable


== Supported Features ==

*) Access of root certificate via HTTP-GET http://localhost:8080/ca

*) Certificate issuing via sending a Certificate Signing Request (PEM-formatted
   PKCS#10 structure) via HTTP-POST to http://localhost:8080/ca/csr