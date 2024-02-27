FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
COPY target/rest-client-1.0.1.jar /opt/app/
EXPOSE 8082
CMD ["java", "-showversion", "-jar", "/opt/app/rest-client-1.0.1.jar"]