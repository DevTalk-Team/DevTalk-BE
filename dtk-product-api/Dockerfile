FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/dtk-product-api-1.0.jar PaymentService.jar
ENTRYPOINT ["java", "-jar", "PaymentService.jar"]
