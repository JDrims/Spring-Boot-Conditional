FROM openjdk:21
EXPOSE 8081
COPY target/conditional-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]