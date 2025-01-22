FROM openjdk:17-jdk-alpine
MAINTAINER com.qre
COPY fbk/fbk_api/target/fbk_api-1.0-SNAPSHOT.jar fbk_api.jar
ENTRYPOINT ["java", "-jar", "/fbk_api.jar"]
EXPOSE 8080