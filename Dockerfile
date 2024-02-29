FROM openjdk:21-slim
COPY target/rest-client-1.0.2.jar /opt/app/
EXPOSE 8082
CMD ["java", "-showversion", "-jar", "/opt/app/rest-client-1.0.2.jar"]