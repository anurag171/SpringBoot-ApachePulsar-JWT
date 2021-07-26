

# Iot Data and Metric Api

The project consist of 5 services as follows
1. **Iotclient service** **(Port : 8071)**
```
This service is responsible for acting as simulator to emit data from iot devices.
 Application uses apache pulsar as distributed streaming platform  to publish data emitted by 
 iot simulators. I have used one topic :-iot-topic for this.
  For demo purpose I have assumed that following data type of data will be emitted by each device
```


IOT DATA
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
`deviceId` | `string` | **Required**. Unique Id of a IOT device |
`deviceName` | `string` | **Required**. Unique Name of a IOT device
`deviceGroup` | `string` | **Required**. Unique Name of a Group of IOT device
`readingType` | `string` | **Required**. Denoting whether it is temperature or heart rate
`reading ` | `string` | **Required**. value of reading
`date` | `string` | **Required**. Denoting the time

Each device can emit data. The value of reading is random value generated between high and low of reading 
maintained in master table.

IOTmaster

Sample reading from iotmaster.

  
  "deviceId": "1a61901c-b6aa-4cca-be3c-fadfc4fed525",
  "deviceName": "HRM",
  "deviceGroup": "HealthDevice",
  "readingType": "HeartRate",
  "maxReading": 100,
  "minReading": 60,
  "_class": "com.anurag.iot.client.data.IotDataMaster"


2. **Iot Data consumer** : **(Port : 8070)**

This service has a event driven consumer responsible for consuming the  data from topic and
writing to mongodb collection. The collection name is IOTDATA.

Write now for demo purpose it simply reads the data and pushes it to mongo db with simple validation
of checking if the fields are not null. However we could use various type of transformation 
based on input data and than map it to one standard form.


3.** IOT Discovery** **(Port : 8069)**

This service is a standard eureka discovery server which registers the metric api. 

4. **IOT Gateway**** (Port : 8068)**

This service is a standard API Gateway which will forward all the data from client to api.
This service also acts as a load balancer and will fetch all the instances of api registered with
eureka server and route the request in round robin fashion.

5. IOT Metric api (Port : 8072)

This service details are given below. This is a secure api which exposes 9 endpoints including 
the login endpoint for getting the token.


  




## IOT Metric Api Reference

#### POST /login. Accepts application/json

Takes username and password and returns the sum.

For demo purpose i have used only two users.

```

| username | password     | 
| :-------- | :------- | 
| `user` | `user` | 
| `admin` | `user` | 


#### Get max , min ,averag and median of reading by sensor name for specic time iterval.
Accepts application/json

```http
  GET /v1/getmaxbysensoranddatetimebetween
  GET /v1/getminbysensoranddatetimebetween
  GET /v1/getaveragebysensoranddatetimebetween
  GET /v1/getmedianbysensoranddatetimebetween
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `sensor` | `string` | **Required**. Name of sensor |
| `startdate` | `string` | **Required**. start date in YYYY-MM-DD HH:MM:SS |
| `enddate` | `string` | **Required**. end date in YYYY-MM-DD HH:MM:SS  |

#### Get max , min ,averag and median of reading by sensor group name for specic time iterval.
Accepts application/json

```http
  GET /v1/getmaxbysensorgrpanddatetimebetween
  GET /v1/getminbysensorgrpanddatetimebetween
  GET /v1/getaveragebysensorgrpanddatetimebetween
  GET /v1/getmedianbysensorgrpanddatetimebetween
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `sensorgrp` | `string` | **Required**. Name of sensor group |
| `startdate` | `string` | **Required**. start date in YYYY-MM-DD HH:MM:SS |
| `enddate` | `string` | **Required**. end date in YYYY-MM-DD HH:MM:SS  |



  
  
## Demo

Insert gif or link to demo

Swagger link

http://localhost:8072/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/Api%20for%20Max%20Reading%20for%20Sensor%20Group



  
## Deployment



  
## Documentation

[Documentation](https://linktodocumentation)

  
    
## Run Locally

Run the project locally you need to have pulsar running on your docker.
You can run following command into command line to start apachae pulsar 

docker run -d -it -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data  apachepulsar/pulsar:latest  bin/pulsar standalone

Once done

Clone the project into your favourite ide

```bash
  git clone https://github.com/anurag171/finalsubmission
```

Go to the project directory finalsubmission

```bash
  cd /DataStore/  
  docker build -t am17docker/iotconsumer .
  cd ../IotClient/  
  docker build -t am17docker/iotclient .
  cd ../IotMetricsApi/  
  docker build -t am17docker/iotmetricapi .
  cd ../MetricDiscovery/  
  docker build -t am17docker/iotdiscovery .
  cd ../MetricGateway/ 
  docker build -t am17docker/iotgateway .
  cd ..
  docker-compose up
  
  if docker-compose is not running properly than we can do following
  
  docker run -d -it -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data  apachepulsar/pulsar:latest  bin/pulsar standalone
  docker run -d -it -p 8071:8071 am17docker/iotclient
  docker run -d -it -p 8070:8070 am17docker/iotconsumer
  docker run -d -it -p 8069:8069 am17docker/iotdiscovery
  docker run -d -it -p 8068:8068 am17docker/iotgateway
  docker run -d -it -p 8072:8072 am17docker/iotmetricapi
  
  
  
  
  
 




  
  
```

  
## Usage/Examples

run the swagger ui
1. Step one click on /login end point
2. Click on try now
3. Put username -->user or admin
4. Put password --> user
5. Click on execute
6. Copy the token
7. Click on right top on Authorize
8. Input the token obtained in value field and click on Authorize
9. Now we can try any api

Sample Data :

Sensor Group

1. HealthDevice
2. ThermostatDevice
3. MeasuringDevice

Sensor Name

1. HRM
2. FitBand
3. Oxymeter

startdate and enddate Examples
2021-06-08 00:00:00
2021-06-08 23:59:59


## Screenshots

Design Diagram

![MetricApi_new](https://user-images.githubusercontent.com/59208873/121073031-ab4d1980-c7ef-11eb-9c7b-3db8985bf1ba.jpeg)


Kubernetes Deployement


![k8's-deployment](https://user-images.githubusercontent.com/59208873/126972256-9d4a31dc-3af1-4cb2-9a4e-8755cbaf500d.png)


![statusk8](https://user-images.githubusercontent.com/59208873/126972228-958fc784-dce3-4a7d-bce2-53914c64fcc7.PNG)



  **Swagger**
Type the swagger url in your favourite browser and following page will appear
http://localhost:8072/webjars/swagger-ui/index.html
![image](https://user-images.githubusercontent.com/59208873/120905103-71540a00-c66d-11eb-89e0-e93b286ef1fc.png)


**Type in search box /v3/api-docs and click on explore. The api list will appear**
![image](https://user-images.githubusercontent.com/59208873/120905153-c55eee80-c66d-11eb-8d73-192021cc4da9.png)

**PostMan Client**
![image](https://user-images.githubusercontent.com/59208873/121073537-40501280-c7f0-11eb-8492-a6661488d862.png)


![image](https://user-images.githubusercontent.com/59208873/121073648-6675b280-c7f0-11eb-9018-9a9d755f91e5.png)

Token setting

![image](https://user-images.githubusercontent.com/59208873/121073779-9a50d800-c7f0-11eb-99fc-e1bab1b6fb27.png)




