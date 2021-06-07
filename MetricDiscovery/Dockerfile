FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
VOLUME /main-app
ADD target/iotdiscovery.jar iotdiscovery.jar
EXPOSE 8069
ENTRYPOINT ["java", "-jar","/iotdiscovery.jar"]