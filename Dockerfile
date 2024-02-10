FROM openjdk:21-slim
COPY target/rest-client-1.0.0-SNAPSHOT.jar rest-client-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rest-client-1.0.0-SNAPSHOT.jar"]