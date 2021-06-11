FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
WORKDIR /app
COPY --from=build /app/target/iotdiscovery.jar /app
EXPOSE 8069
ENTRYPOINT ["java", "-jar","iotdiscovery.jar"]