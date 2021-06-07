FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
VOLUME /main-app
ADD target/iotclient.jar iotclient.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar","/iotclient.jar"]