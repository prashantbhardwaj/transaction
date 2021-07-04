FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/transaction-*.jar
COPY ${JAR_FILE} transaction-app.jar
ENTRYPOINT ["java","-jar","/transaction-app.jar"]