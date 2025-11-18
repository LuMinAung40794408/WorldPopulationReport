FROM eclipse-temurin:18
COPY ./target/WorldPopulation.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "WorldPopulation.jar", "db:3306", "30000"]