FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
VOLUME /main-app
ADD target/iotmetricapi.jar iotmetricapi.jar
EXPOSE 8072
ENTRYPOINT ["java", "-jar","/iotmetricapi.jar"]