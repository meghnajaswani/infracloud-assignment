# Use Eclipse Temurin JDK 21 (ARM compatible)
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy your built jar (replace with actual jar filename)
COPY target/urlshortener-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]