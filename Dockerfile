FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/selapak-api-0.0.1-SNAPSHOT.jar selapak-app.jar
ENTRYPOINT ["java","-jar","/selapak-app.jar"]