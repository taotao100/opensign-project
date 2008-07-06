*******************************************************************************
*                   OPEN SIGN SERVER - STARTING SERVER                        *
*******************************************************************************

This file describes how the server is configured and fired up. Furthermore, a 
brief overview of functions is given.


== Prerequisites ==

	o Install Java runtime 1.6

	o Download and unpack OpenSignServer-XX.tar.gz package


== Configuration ==

	o Configuration file: config.properties

	o Configuration of log method in file: log4j.properties
 

== Starting the Server ==

	o Windows: Click on run.bat

	o Linux: java -jar jar\OpenSignServer-0.1.jar

	Server starts at: http://localhost:8080 ,whereas the port is configurable


== Supported Features ==

	o Access of root certificate via HTTP-GET http://localhost:8080/ca

	o Certificate issuing via sending a Certificate Signing Request (PEM-formatted
      PKCS#10 structure) via HTTP-POST to http://localhost:8080/ca/csr