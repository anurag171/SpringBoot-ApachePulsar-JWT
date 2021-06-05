package com.anurag.iot.client.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

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
