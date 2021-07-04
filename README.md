# Transaction APIs

## How to run the application 
####(Application runs on 8081 by default)

### In IntelliJ IDE
   <br>        Import project in your IDE and Run SpringBoot class TransactionSystemApplication
   
### Using Gradle
Run following command

`gradle bootRun`
   
### Using JAR file
Create JAR file using following command

`gradle bootJar`

and run the application from jar using

   `java -jar build\libs\transaction-system-0.0.1-SNAPSHOT.jar`

### Using Docker Image
Create Docker image using following command

`docker build --build-arg JAR_FILE=build/libs/*.jar -t <docker-id>/transaction-api .`

Push your image into DockerHub using command 

`docker push <docker-id>/transaction-api:latest`

An image of this project can be found [here](https://hub.docker.com/repository/docker/amuprashant/transaction-api).
Run that image using command

`docker run -p 8081:8081 --name myapp -d amuprashant/transaction-api:latest`

## Documentation

Application uses **[Spring MockMvc](https://docs.spring.io/spring-framework/docs/current/reference/html/#spring-mvc-test-framework)** to test the Rest APIs. Combining **[Spring Rest Docs](https://docs.spring.io/spring-restdocs/docs/2.0.5.RELEASE/reference/html5/)** with MockMVC gives us chance to collect the real request and response data in the form of snippets.

[Asciidoctor](https://asciidoctor.org/) is used to convert these snippets into a beautiful html page. This page is being transported within the application jar as a static resource, and can be referenced on this URL -

http://localhost:8081/docs/index.html


### Other references
https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code

https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications

https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing


