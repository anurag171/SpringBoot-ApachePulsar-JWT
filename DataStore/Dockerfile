FROM openjdk:8-jdk-alpine
LABEL maintainer="anurag171@gmail.com"
VOLUME /main-app
ADD target/iotdatastore.jar iotdatastore.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar","/iotdatastore.jar"]