package com.anurag.iot.client.initialize;

public enum DeviceEnum {	
	
	
	HEARTRATE("HRM","HeartRate","HealthDevice","100","60"),
	THERMOSTAT("Tempurature Sensors","A.C temperature","ThermostatDevice","300","90"),
	CARFUELREADING("Fuel Sensor","Fuel Gauge","MeasuringDevice","55","5");
	
	private final String name;
	private final String title;
	private final String group;
	private final String maxReading;
	private final String minReading;
	
	private DeviceEnum(String name,String title, String group,String maxReading,String minReading) {
		this.name = name;
		this.title = title;
		this.group = group;
		this.maxReading = maxReading;
		this.minReading = minReading;
	}

	public String getTitle() {
		return title;
	}

	

	public String getGroup() {
		return group;
	}

	

	public String getName() {
		return name;
	}


	public String getMaxReading() {
		return maxReading;
	}

	public String getMinReading() {
		return minReading;
	}
}
