FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/dtk-matching-api-1.0.jar MatchingService.jar
ENTRYPOINT ["java", "-jar", "MatchingService.jar"]
