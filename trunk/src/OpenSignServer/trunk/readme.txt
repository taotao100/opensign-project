*******************************************************************************
*                        OPEN SIGN SERVER - README                            *
*******************************************************************************


This file describes how the server is configured and fired up. Furthermore, a 
brief overview of the usage is given.


== Prerequisites ==

	o Install Java runtime 1.6

	o Download and unpack OpenSignServer-XX.tar.gz package

== Database ==
	
	If you intend to use a data base it is necessary to download and install 
	MYSQL and set the storage type to MYSQL in file config.properties. 
	Otherwise the server will choose to run in memory (data is lost if the 
	server is shut down). Make sure the MYSQL database has an account with the 
	user name 'root' and a empty password, which is the default configuration 
	of MYSQL anyway.

== Configuration ==

	o Configuration file: conf/config.properties

	o Configuration of log method in file: conf/log4j.properties	
 
== Starting the Server ==

	o Windows: Click on run.bat

	o Linux: java -jar jar\OpenSignServer-0.1.jar

    Server starts at: http://localhost:8080, whereas the port is configurable


== Usage ==

    o Registering users is possible straight away
    
    o To enable a user to use the certification service the user above (issuer 
      privileges) must approve the user first. Furthermore, it is the choice of 
      the issuer above whether or not the requesting user gets issuer privileges 
      himself.
      
      *************************************************************************
      * NOTE:                                                                 *
      * The default and top-level issuer is "root". After installing the      *
      * server it is necessary to log in using the root-account (user name:   *
      * root; password: 123) and to reset the password!                       *
      *************************************************************************
    
    o Approved users may now issue a certificate sign request (CSR) and 
      retrieve the certificate. The posting of the CSR is possible by using the 
      client application as well as by posting a PEM formatted request by use 
      of the web form online.
      
    o The certificate will be accessible online:
      - at the users profile:
        http://localhost:8080/root/[optional path]/[user name]
      - binary formatted (default):
        http://localhost:8080/root/[optional path]/[user name]?property=cert
      - PEM formatted: 
        http://localhost:8080/root/[optional path]/[user name]
          ?property=cert&responseFormat=PEM
      

