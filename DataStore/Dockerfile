FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
WORKDIR /app
COPY --from=build /app/target/iotdatastore.jar /app
EXPOSE 8070
ENTRYPOINT ["java", "-jar","iotdatastore.jar"]