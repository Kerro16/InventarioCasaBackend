FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY build/libs/InventarioCasaBackend-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]