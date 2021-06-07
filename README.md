
**Flow Diagram**
![Metric _Api2](https://user-images.githubusercontent.com/59208873/120912949-180dca00-c6b1-11eb-9e7b-3eb0d97b520a.jpeg)


**Swagger**
Type the swagger url in your favourite browser and following page will appear
http://localhost:8072/webjars/swagger-ui/index.html
![image](https://user-images.githubusercontent.com/59208873/120905103-71540a00-c66d-11eb-89e0-e93b286ef1fc.png)


**Type in search box /v3/api-docs and click on explore. The api list will appear**
![image](https://user-images.githubusercontent.com/59208873/120905153-c55eee80-c66d-11eb-8d73-192021cc4da9.png)




# Iot Data and Metric Api

The project consist of 5 services as follows
1. Iotclient service
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


2. Iot Data consumer : 

This service has a event driven consumer responsible for consuming the  data from topic and
writing to mongodb collection. The collection name is IOTDATA.

Write now for demo purpose it simply reads the data and pushes it to mongo db with simple validation
of checking if the fields are not null. However we could use various type of transformation 
based on input data and than map it to one standard form.


3. IOT Discovery

This service is a standard eureka discovery server which registers the metric api. 

4. IOT Gateway

This service is a standard API Gateway which will forward all the data from client to api.
This service also acts as a load balancer and will fetch all the instances of api registered with
eureka server and route the request in round robin fashion.

5. IOT Metric api

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

