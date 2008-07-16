*******************************************************************************
*                        OPEN SIGN SERVER - README                            *
*******************************************************************************

This file describes how the server is configured and fired up. Furthermore, a 
brief overview of supported features is given.


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

  o Demo-wise set up of an X.509 hierarchy intending to provide code siging 
    certificates. This involves one root issuer, an unlimited number of sub-
    issuers and end-users.     
  o End-users may issue a certificate sign request and obtain the certificate 
    in return.
	o Demo accounts of to end-users ("user1", "user2") and two issuers ("root", 
    "user3") each with password "123".    
	o Possibility for registering new end-users and issuers.
  o	Session handling - login, logout of users    
  o Storage of issuer key-pair's and all certificates in server side key store.
	o Public access of all certificates in the system, with support of binary 
    and PEM format. Eg.: Certificate from root issuer may be retrieved 
    - in binary format (default): 
    		http://localhost:8080/root?property=cert
    - or PEM formatted: 
    		http://localhost:8080/root?property=cert&responseFormat=PEM     
  o User/resource profile, which is accessible at the resource path without 
    further parameters, eg.: http://localhost:8080/root/user1
