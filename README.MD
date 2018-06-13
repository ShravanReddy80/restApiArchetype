To run the REST API...

1) mvn clean install
2) cd web
3) mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=prod -Ddebug=true" -Dfork=true
 

This will run the app on tomcat at the default port 8080


