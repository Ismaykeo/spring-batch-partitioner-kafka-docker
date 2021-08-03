FROM adoptopenjdk/openjdk11
ADD target/spring-batch-partitioner-kafka-docker-0.0.1-SNAPSHOT.jar app.jar
ADD files/employee.csv /files/employee.csv
ENTRYPOINT ["java", "-jar", "/app.jar"]