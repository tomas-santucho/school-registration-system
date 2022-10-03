FROM openjdk:18-jdk-alpine
VOLUME /main-app
ADD build/libs/SchoolSystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]