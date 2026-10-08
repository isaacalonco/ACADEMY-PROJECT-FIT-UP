# ---- Build Stage ----
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy pom and download dependencies first (cache layer)
COPY backend/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy source code and frontend (needed for StaticFileHandler)
COPY backend/src ./src
COPY frontend ./frontend

# Build the fat JAR
RUN mvn package -DskipTests -B

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/academy-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Copy frontend files (the Java server serves them)
COPY --from=build /app/frontend ./frontend

# Railway injects PORT env var at runtime
EXPOSE ${PORT:-8080}

CMD ["java", "-jar", "app.jar", "--server"]
