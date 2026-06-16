# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and .mvn/ first for dependency caching
COPY pom.xml .
COPY .mvn/ .mvn/

# Download dependencies offline
RUN mvn dependency:go-offline

# Copy source code
COPY src/ src/

# Build the application (skip tests for faster build in Docker)
RUN mvn package -DskipTests

# Stage 2: Runtime with JRE only
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
