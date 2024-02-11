FROM openjdk:21-slim
COPY target/rest-client-1.0.1-SNAPSHOT.jar rest-client-1.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rest-client-1.0.1-SNAPSHOT.jar"]