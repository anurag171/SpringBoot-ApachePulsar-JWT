package com.anurag.iot.data.api.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "iotdata")
public class IotDataModel {

    @Id
    private String id;
    //Unique Device Id
    private String deviceId;

    //Device Name
    private String deviceName;

    //Device group
    private String deviceGroup;

    //ReadingType
    private String readingType;

    //Reading
    private Double reading;

    private Date date;

}

