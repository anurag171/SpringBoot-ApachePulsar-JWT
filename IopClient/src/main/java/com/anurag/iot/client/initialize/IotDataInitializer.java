package com.anurag.iot.client.initialize;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.anurag.iot.client.data.IotData;
import com.anurag.iot.client.repository.IotDataReactiveRepository;
import com.anurag.iot.client.service.Producer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class IotDataInitializer implements CommandLineRunner { 

	@Autowired
	Producer producer;

	@Autowired
	IotDataReactiveRepository iotDataReactiveRepository;

	@Value("${iot.devices.count:3}")
	Integer noOfDevices;

	@Value("${iot.data.emit.frequency.in.seconds:1}")
	Integer frequencyInSeconds;



	@Override
	public void run(String... args) throws Exception {

		initalDataSetUp();       
	}



	public List<IotData> data() {

		List<IotData>  data =  new ArrayList<>();

		int count =0;
		Arrays.asList(DeviceEnum.values()).stream().forEach(device->{
			IotData sample = new IotData(UUID.randomUUID().toString(), device.getName(), device.getGroup(), device.getTitle(), Double.MIN_VALUE);
			data.add(sample);
		});

		return data;
	}

	public void dataSetUpforCappedCollection(IotData data) throws PulsarClientException{


		Flux<IotData> itemCappedFlux = Flux.interval(Duration.ofSeconds(frequencyInSeconds))
				.map(i -> getIotData(data));

		itemCappedFlux.subscribe(i->{
			try {
				producer.send(i);
			} catch (PulsarClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//throw e;
			}
		});       
	}

	private IotData getIotData(IotData data) {
		
		String health = DeviceEnum.HEARTRATE.getName();
		IotData generatedData = null;
		
		switch(data.getDeviceName()) {
		case "HRM" :
			generatedData = new IotData(data.getDeviceId(),data.getDeviceName(), data.getDeviceGroup(), data.getReadingType(), Double.valueOf(new Random().nextInt(100 - 60) + 60));
		case "Tempurature Sensors" : 
			generatedData = new IotData(data.getDeviceId(),data.getDeviceName(), data.getDeviceGroup(), data.getReadingType(), Double.valueOf(new Random().nextInt(300 - 90) + 90));
		case "Fuel Sensor" : 
			generatedData = new IotData(data.getDeviceId(),data.getDeviceName(), data.getDeviceGroup(), data.getReadingType(), Double.valueOf(new Random().nextInt(55 - 1) + 1));
			
		}
		return generatedData;

	}



	private void initalDataSetUp() {

		iotDataReactiveRepository.deleteAll()
		.thenMany(Flux.fromIterable(data()))
		.flatMap(iotDataReactiveRepository::save)
		.thenMany(iotDataReactiveRepository.findAll())
		.subscribe((item -> {
			System.out.println("Iotdata inserted from CommandLineRunner : " + item);
			try {
				dataSetUpforCappedCollection(item);
			} catch (PulsarClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}));                
	}
}
