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

    o Certificate chains are now set up properly. This includes the right values 
      in the certificate as well as appropriate key-handling of the key store.        
      Dummy code got removed broadly. 
  	o This version supports the use of OSSJClient version 0.9 for commands 
  	  "getcert", "verifycert" and "csr" 
  

