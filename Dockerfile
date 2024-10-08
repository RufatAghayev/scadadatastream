FROM debian:bullseye-slim

# Install OpenJDK 17 and Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files into the container
# This assumes that your Maven project structure is already set up correctly
COPY . .

# Build the Maven project
# This step is not necessary if you have the JAR file ready
# You can comment out this line if you're copying a pre-built JAR file
# RUN mvn clean package

# Define the command to run your Spring Boot application
# This will start your application when the container starts
CMD ["java", "-jar", "scadadatastream-0.0.1-SNAPSHOT.jar"]
