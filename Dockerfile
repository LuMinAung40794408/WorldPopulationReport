FROM eclipse-temurin:18
COPY ./target/WorldPopulation-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "WorldPopulation-0.1.0.2-jar-with-dependencies.jar"]