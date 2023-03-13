# userapi
Java Spring Boot Demo  

# Scope of work
A program that will: 
1. Do search and export maximum of 100 Users from Github into PDF Format. 
2. Do list of export history and able to download existing pdf
Program specification as below:
1. Use REST API endpoint to perform export PDF (any endpoint name is fine)
2. Use REST API endpoint to perform list of export history
3. Use REST API endpoint to download existing exported PDF
4. Defined Json Request Payload and  Postman collection
5. Defined Json Response of bad response/no record found
6. Use JAVA with Spring Boot Framework
7. Good Code Structure
8. Good Naming Convention
9. Create JUnit Test to handle code coverage
10. Create MySQL database if required to stored exported PDF history
Reference Github Public API to integrate (Or can find similar API):  
https://docs.github.com/en/rest/search?apiVersion=2022-11-28#search-users



# To open project using Eclipse
1. Import project from git with the url -> https://github.com/johan-i-zahri/usersapi.git  
	you will need to Import it as a general project by doing the following:  
	File > Import  
	>> choose/type Projects from Git  , next  
	>> choose Clone URI  , next  
	>> https://github.com/johan-i-zahri/usersapi.git and credentials , next  
	>> choose all branch, next  
	>> choose target directory, choose main as inital branch, next  
	>> choose import as general, next  
	>> finish  
2. Convert the imported project into maven project by right clicking the project and choose  
	Configure > Convert to Maven Project
3. The debugging configuration should be automatically loaded. To access them goto:  
	Debug icon drop down > Debug Configurations...  
	
# To create the database
Run the extras/sql/ddl.sql  

# To run the coverage test 
Right click on the project and go to Coverage As>JUnit Test  

# To import the postman collection
Use the files in extra/postman/test.postman_collection.json
