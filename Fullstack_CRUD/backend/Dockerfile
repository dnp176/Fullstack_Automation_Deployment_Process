# Stage 1: Build
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Copy Maven Wrapper and Project Configuration Files
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the packaged application from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 7001

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]