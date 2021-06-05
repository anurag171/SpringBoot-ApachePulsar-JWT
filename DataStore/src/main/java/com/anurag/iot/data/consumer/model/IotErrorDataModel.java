package com.anurag.iot.data.consumer.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ioterrordata")
public class IotErrorDataModel {

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
	
	//List of validation missing
	private List<String> error;
	
	private Date date;

}
