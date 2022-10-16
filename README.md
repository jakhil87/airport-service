# airport-service #
Spring boot app to retrieve aiport information worldwide.
Following libraries/features are used.
* [Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)
* [Security](https://docs.spring.io/spring-security/reference/index.html)
* [spring-data-jpa](https://spring.io/projects/spring-data-jpa)
* [spring-batch](https://spring.io/projects/spring-batch): for loading files into database
* [spring-sleuth](https://spring.io/projects/spring-cloud-sleuth): For tracing
* [Lombok](https://projectlombok.org/)
* [OpenApi - Swagger UI](https://springdoc.org/)

Initial data load is performed using spring batch in a 3 steps job to load 3 different files.

### Api Endpoints details: ###
Security: Security filter is registered to allow Username password based basic authentications on api endpoints. Specific 
apis are annotated with @RolesAllowed to check authorizations. Below users are set in-memory to authenticate.
    
    User 1: app-user1    password: user-pass1     role: USER
    User 2: app-user2    password: user-pass2     role: ADMIN

#### Browse API documentation at: [Swagger Endpoint](http://localhost:8080/swagger-ui/index.html) ####
1. Get list of countries with max number of airports.

        Endpoint: /api/countries-with-max-airports
        Request param: maxResults (optional parameter to limit response. Default value is 10)
        Roles allowed: "USER", "ADMIN"
2. Get list of runways for a country search by Code(exact match) or Name(ignore case)
        
        Endpoint: /api/runways-by-country
        Request param: search (Mandatory parameter to filter a country by its code or its name)
        Roles allowed: "USER", "ADMIN"
3. Get list of countries with airports and runways filtered by partial search string.

        Endpoint: /api/countries-by-name
        Request param: search (Mandatory parameter to filter a country by its name partially. Minimum 3 characters needed for search)
        Roles allowed: "ADMIN" - please note for demonstration this endpoint is only allowed for admin role

### Implementation Details - Running and testng the APP ###
* Initial data load is performed using spring batch in a 3 steps job to load 3 different files.
* In memory database is used with spring jpa repositories to query data.
* Apis are secured by basic authentication
* Application is configured to run in `dev` profile by default and can run locally using below ways
  * IDE: Use Intellij or similar IDE to run as spring boot application. Dev profile is already activated by default.
  * Maven:  `mvn spring-boot:run` command can be used to run as maven.
  * Jar: spring boot executable jar generated in target folder can be run using `java -jar airport-service-<version>.jar` command
         after `mvn clean package` or `mvn clean install` command.
* **Testing the APIS: A detailed Postman Collection is provided at location:** [Postman Collection](src/test/resources/postman/airport-service.postman_collection.json)
* Application is configured to run unit-test cases using **Surefire Plugin** in `mvn test` phase and integration-test using **FailSafe Plugin** in `mvn verify` or higher stage.
  * Test classes ending with IT will only run during integration-test phase to quickly run unit-test with mvn test phase.
  * The coverage reports can be accessed under path /target/site/jacoco-* after running `mvn verify` or higher phase.
### Details for running the application and test the apis ###
