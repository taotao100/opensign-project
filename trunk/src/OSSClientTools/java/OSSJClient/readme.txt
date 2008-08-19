*******************************************************************************
*                        OPEN SIGN SERVER - README                            *
*******************************************************************************


This file describes how the server is configured and fired up. Furthermore, a 
brief overview of supported features is given.


== Prerequisites ==

	o Install Java runtime 1.6

	o Download and unpack OpenSignServer-XX.tar.gz package

== Database ==
	
	If intending to use a data base one will need to download and install MYSQL
	data base and set the storage type to MYSQL in file config.properties.
	Make sure the MYSQL database has a account with the user name 'root' and
	empty password, which is the default configuration of MYSQL anyway.

== Configuration ==

	o Configuration file: conf/config.properties

	o Configuration of log method in file: conf/log4j.properties
 

== Starting the Server ==

	o Windows: Click on run.bat

	o Linux: java -jar jar\OpenSignServer-0.1.jar

    Server starts at: http://localhost:8080 ,whereas the port is configurable


== Supported Features ==

  o Easy extendable persistence layer, which is set up using Hibernate – 
    Annotations.
	o Possibility to run server in memory, whereas data is lost when the server 
    process is terminated, or to run the server on top of a MYSQL database.
	o Logging mechanism got enhanced which involves means to pipe the log 
    information from OpenSign server as well as from Jetty and Hibernate to a 
    log file.
	o Same functionality as version 0.2 from a user point of view. Please consult:
	  https://www.owasp.org/index.php/Project_Information:template_OpenSign_Server_Project
	  for further information.  

