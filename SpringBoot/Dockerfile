# Use the JDK 23 base image
FROM eclipse-temurin:23

# Install Mavens (this is because there's no official Maven image with JDK 23)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Sets the working directory inside the container
WORKDIR /app

# Copies the entire SpringBoot project into the container
COPY . .

# In case you are running into a linux system, give permissions for Maven configuration
RUN chmod +x mvnw

# Runs the application (it skips the tests for efficiency)
RUN ./mvnw clean package -DskipTests

# Expose the port your application listens on
EXPOSE 8080

# Run the Spring Boot application (basically, "java, run the -jar comamnd in this specific jar file)
CMD ["java", "-jar", "target/SpringBoot-0.0.1-SNAPSHOT.jar"]
