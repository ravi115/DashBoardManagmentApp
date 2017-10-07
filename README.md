# DashBoardManagmentApp

This application exposes the **GET, POST, PUT, DELETE API** of Restful web services and provides an **_interface_** to perform **CRUD Operations**.
In this application we can add _new user_ by providing user name and description for this user. on later stage we can update user information and also we can delete the existing user details based on _name, id and description_. this application provides a strong **_search engine_** which let you to search any kind of data available.

### Building environment of this application.

for **_backend developement_**
- Eclipse IDE (Oxygen beta version)
- Apache Tomcat Server-9.0v 
- MySQL Database -5.6v
- Maven Project Framwork- 4.0.0v

for **_frontend development_**
- HTML5
- BootStrap 4.0.0-alpha.6
- jQuery 3.2.1-jquery.min.js
- javaScript

### Important Library Used.
- mysql-connector-java- 6.0.5
- commons-configuration2- 2.0
- junit framwork- 4.10
- Jersey framwork- 1.19
- Hibernate Framwork - 5.2.11.Final

### Instructions to run the application.
```diff
+before running the application we have to create a database called dashboard in mySQL.
```
1. if you want to use different database which already exist for you then change the database name in hibernate.cfg.xml file [click here](https://github.com/ravi115/DashBoardManagmentApp/blob/master/DashBoardManagementApp/src/hibernate.cfg.xml).
update this property
```diff
<property name="connection.url">jdbc:mysql://localhost:3306/dashboard-name</property>
```
in hibernate.cfg.cml file.

2. in order to make successfull database connection with our application, we require database credentials. in this application the database credential is 

```diff 
+username = root & password = root
```

so if you want to add your credential, you can add in hibernate.cfg.xml file [click here](https://github.com/ravi115/DashBoardManagmentApp/blob/master/DashBoardManagementApp/src/hibernate.cfg.xml). 
update this property with your credentials: - 
```diff 
 <property name="connection.username">root</property>
 <property name="connection.password">root</property>
 ```

### To run this application follow these below steps.
1. deploye the war file of this application on tomcat server.
2. to download war file [click here](https://github.com/ravi115/DashBoardManagmentApp/blob/master/DashBoardManagementApp.war).
3. once you successfuly deployed the war file hit this below link to perform your operation.
  ```diff
- http://localhost:8080/DashBoardManagementApp/index.html
```
4. check you port in which tomcat is running. if it is not 8080 then change the port number in the above url.
**NOTE: - _in this case your credential should be same as the credential used in this application and also database name should be same._

### To test API of this application follow these below steps. 
1. **To test GET API of this application: -**

To list down all user from database.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/search
```
To list down all user from database based on user id.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/search?id=1
```
To list down all user from database based on user name.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/search?name=ravi
```

To list down all user from database based on user description.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/search?description=i am an engineer!
```

2. **To test POST API of this application:-**

To add new user.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/insertdata

with JSON Body
{
	"name":"Ravi Ranjan Kumar",
	"description":"i am an engineer!"
}
```
3. **To test PUT API of this application:-**

To update existing user details.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/update

with form message Body
id: 1 (manadatory field)
name : ravi ranjan (optional)
description: i love singing. (optional)
```
4. **To test DELETE API of this application:-**

To update existing user details.
```diff
http://localhost:8080/DashBoardManagementApp/rest/resources/remove

with form message Body
id: 1 (optional)
name : ravi ranjan (optional)
description: i love singing. (optional)
```

**Note: - search _doc folder_ to  get java document of this application**.

========================================================= o End o ==================================================================
