FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
VOLUME /main-app
ADD target/iotgateway.jar iotgateway.jar
EXPOSE 8068
ENTRYPOINT ["java", "-jar","/iotgateway.jar"]