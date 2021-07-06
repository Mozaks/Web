FROM openjdk:14-jdk-alpine
EXPOSE 8080
COPY ./target/demo-0.0.1-SNAPSHOT.jar web.jar
ENTRYPOINT ["java","-jar","web.jar"]