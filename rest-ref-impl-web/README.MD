The Application.java class needs to be in the base package of the project(overall, not just the '-web' module). That ensures that all the configuration files/classes are picked up at startup. Otherwise, you will get weird errors that will be hard to debug.

All the configuration necessary for the '-web' module is in the '-config' module. I have done this so that all the configuration information is in one place. Spring and Maven are  smart enough to figure it out.

The context root or contextPath of the app is configured in the application.properties. Check for 'server.contextPath' property in the properties file.

When you call the REST url from a browser, you will either get an error if the resonse is test or in XML if the controller method supports it.
Even if it supports JSON, the response will be in XML. You will get the JSON response only if you call the URL from a REST client.

---------------------------------------------------------------
REST Interface
Root : http://localhost:8080/ref-impl/
Customers: http://localhost:8080/ref-impl/customers/

H2 console: localhost:8080/ref-impl/console/login.jsp
		Use JDBC URL = jdbc:h2:mem:example-app

SWAGGER DOCS:
Home page : http://localhost:8080/ref-impl/swagger-ui.html 
Swagger as json : http://localhost:8080/ref-impl/v2/api-docs

---------------------------------------------------------------


The steps to follow to create and make the archetype available in the maven repository are shown in http://www.codingpedia.org/ama/how-to-create-a-multi-module-project-with-maven-template . 