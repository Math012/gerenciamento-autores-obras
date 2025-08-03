FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/autores-obras-0.0.1-SNAPSHOT.jar /app/autores-obras.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/autores-obras.jar"]