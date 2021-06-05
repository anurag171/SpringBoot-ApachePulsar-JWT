package com.anurag.iot.data.consumer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Generic data type to read store IOT data emitted
 * @author 91982
 *
 */

@Data
@AllArgsConstructor
@ToString
public class IotData {
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
	
}
