FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
WORKDIR /app
COPY --from=build /app/target/iotgateway.jar /app
EXPOSE 8068
ENTRYPOINT ["java", "-jar","iotgateway.jar"]