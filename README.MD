To run the REST API...

1) mvn clean install
2) cd web
3) mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=prod -Ddebug=true" -Dfork=true
 

This will run the app on tomcat at the default port 8080


##############################################

Requirements

•	As reference, let's start with an ref baseline, possibly this is the Spring-based services backing in ThingBook or CIAM Profile. Rafal and Abhijit can help identify which Java projects should be included as a possible reference.

•	Plan the Java class scaffolding that adhere to the ref Java coding standards. Considering scaffolding, this includes folder and namespace layout. -->DONE

•	Loading of configuration from external file and/or environment (reading of env. variables for DB properties, for example). --> DONE

•   Logging support including structure logging support. Scaffolding should some degree of logging, probably the easiest is logging at the top-layer of request/response Web API processing. --> DONE

•	Configuration of Dependency Injection.  Dependencies being the repository classes (DAOs) wrapping the data persistence tier (O/R via JDBC, JPA, NoSQL-based MongoDB, etc.).

•	Scaffolding should include the mapping of high-level modeling classes to lower-level data-oriented classes.
Error handling should include mapping of exception to HTTP status codes put on the wire and return to the client (500, 404, etc.)

•	Scaffolding should include boilerplate code to handling request object validation (typically model-level validation of input arguments)

•	Implementation of filters in the HTTP-based API request pipeline. (Example, can be used to validate request object.)

•	Definition of exception handler in the scaffolding (can be used to help support of the HTTP status error handling).

•	Scaffolding of config and API code should bring in the necessary Swagger support.

•	As part of a data-oriented scaffolding Web API project, the generated boiler plate code should have paging and sorting including (this as part of the response object handling of collections).

•	The scaffolding of the unit test config and classes should bring in a mocking library. Using Spring IoC, we should at least mock out external dependencies (e.g. database).

•	The test code scaffolded should include unit testing where the DAO is mocked and the request/response controller classes (as well as filters) are exercised.

•	Versioning should be included in the scaffold to point developers in the preferred direction (e.g. header-based versioning).

•	The above considers a project template for the above data-oriented Spring / Spring Boot Web API. However, we should also have a more advanced template generator that brings in the Netflix (OSS) Spring Cloud project artifacts.

•	Details of service discovery and Eureka, gatekeeper with Zuul, etc.

•	Considering advanced aspects of Spring Cloud, scaffold code of the project template could provide a framework example of how to apply the circuit-breaker functionality.

Question: Should the scaffold include security aspects or is the assumption that the API gateway will take care for this?  We should consider what aspects of security should be brought into the Spring code layer.

The above alone is still in-complete or at least can be redefined further.  Possibly some of the above can be rolled together in a ref specific form and delivered via a Maven artifact.  For sure much of the above would leverage a project generator (either from the IDE or using a tool such as Yeoman).

I would like to see you and Abhijit (Lele) advance this further.  The output should be a project generator (based on Yeoman or something similar).  We can also think to leverage the facilities of Eclipse or IntelliJ to create a ref specific project type that will generate scaffolding.
