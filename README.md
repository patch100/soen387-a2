# SOEN 387 - Assignment 2

## Database setup:
### First make sure the database is setup properly:
Open mysql and make sure the database revenue exists:
`CREATE DATABASE IF NOT EXISTS revenue;`

### Make sure the user that the application uses exists:
`grant all on ``revenue``.* to ``pyoung``@``localhost`` identified by ``password``;`


# To Run
## Tomcat:
Copy assignment2.war to tomcats webapps folder then make sure tomcat is running: `catalina run`
Access the web app using the following url:
http://localhost:8080/assignment2/contracts

## Troubleshooting Tomcat:
Make sure a tomcat user is set up in tomcat/conf/tomcat-users.xml by adding the following to it:
```
  <role rolename="tomcat"/>
  <role rolename="manager-gui"/>
  <user username="tomcat" password="password" roles="tomcat,manager-gui"/>
```

Then make sure tomcat is running: `catalina run`
Tomcat usually runs on port 8080, so to access the manager app goto 
http://localhost:8080/manager
Then make sure the assignment2.war is present and started

## Glassfish:
`asadmin deploy assignment2.war`
Web application will be accessible at http://localhost:8080/assignment2/contracts
