FROM openjdk:17-jdk-slim

WORKDIR /entrega3dssd

COPY target/demo-0.0.1-SNAPSHOT.jar entrega3dssd.jar

EXPOSE 8080

CMD ["java", "-jar", "entrega3dssd.jar"]