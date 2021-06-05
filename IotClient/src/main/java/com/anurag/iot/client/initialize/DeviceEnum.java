package com.anurag.iot.client.initialize;

public enum DeviceEnum {	
	
	
	HEARTRATE("HRM","HeartRate","HealthDevice"),
	THERMOSTAT("Tempurature Sensors","A.C temperature","ThermostatDevice"),
	CARFUELREADING("Fuel Sensor","Fuel Gauge","ReadingDevice");
	
	private final String name;
	private final String title;
	private final String group;
	
	private DeviceEnum(String name,String title, String group) {
		this.name = name;
		this.title = title;
		this.group = group;
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

	
}
