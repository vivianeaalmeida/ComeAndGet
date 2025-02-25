# Use the JDK 23 base image
FROM maven:3.9-eclipse-temurin-23

# Install Mavens (this is because there's no official Maven image with JDK 23)
#RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Sets the working directory inside the container
WORKDIR /workdir/server

# Copies the entire SpringBoot project into the container
COPY pom.xml /workdir/server/pom.xml

RUN mvn dependency:go-offline

COPY src /workdir/server/src

RUN mvn install

# Expose the port your application listens on
EXPOSE 8080

# Run the Spring Boot application (basically, "java, run the -jar comamnd in this specific jar file)
CMD ["mvn", "spring-boot:run"]
