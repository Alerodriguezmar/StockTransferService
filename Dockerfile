FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY target/InventaryEntryAndExitService-1.0.jar /app
EXPOSE 22100
CMD ["java", "-jar", "/app/InventaryEntryAndExitService-1.0.jar"]