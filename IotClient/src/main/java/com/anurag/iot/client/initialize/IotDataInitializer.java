package com.anurag.iot.client.initialize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import com.anurag.iot.client.data.InputData;
import com.anurag.iot.client.data.IotDataMaster;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
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

	@Value("${load.data.file.path}")
	String filepath;

	private final Double Zero = new Double(0);



	@Override
	public void run(String... args) throws Exception {

		initalDataSetUp();       
	}



	public List<IotDataMaster> data() {

		List<IotDataMaster>  data =  new ArrayList<>();

		int count =0;
		Arrays.asList(DeviceEnum.values()).stream().forEach(device->{
			IotDataMaster sample = new IotDataMaster(UUID.randomUUID().toString(), device.getName(), device.getGroup(),
					device.getTitle(),Double.valueOf(device.getMaxReading()),
					Double.valueOf(device.getMinReading()));
			log.info("Adding master data entry [{}]",sample.toString());
			data.add(sample);
		});

		final StringJoiner joiner = new StringJoiner("");
		try{
			Files.lines(Paths.get(filepath)).map(String::trim)
					.filter(str->!str.isEmpty())
					.forEach(valStr-> joiner.add(valStr));

			InputData jsonData =null;
			if(joiner.length()>0){
				Gson gson =  new Gson();
				List<LinkedTreeMap> listofgson = gson.fromJson(joiner.toString(),List.class);
				listofgson.forEach(inputData -> {
					IotDataMaster entry = new IotDataMaster(UUID.randomUUID().toString(),
							String.valueOf(inputData.get("deviceName")),
							String.valueOf(inputData.get("deviceGroup")),
							String.valueOf(inputData.get("readingType")),
							Double.valueOf(String.valueOf(inputData.get("maxReadingRange"))),
							Double.valueOf(String.valueOf(inputData.get("minReadingRange"))));
					log.info("Adding master data entry [{}]",entry.toString());
					data.add(entry);

				} );
			}

		}catch ( IOException ex){
			log.error("No file or Path exist [{}]",filepath);
		}
		return data;
	}

	public void dataSetUpforCappedCollection(IotDataMaster data) throws PulsarClientException{


		Flux<IotData> itemCappedFlux = Flux.interval(Duration.ofSeconds(frequencyInSeconds))
				.map(i -> getIotData(data));

		itemCappedFlux.subscribe(i->{
			try {
				producer.send(i);
			} catch (PulsarClientException e) {
				// TODO Auto-generated catch block
				log.error("Pulsar Exception [{}]",e.fillInStackTrace());
				//throw e;
			}
		});       
	}

	private IotData getIotData(IotDataMaster data) {
		IotData generatedData = null;

		return	generatedData = new IotData(data.getDeviceId(),data.getDeviceName(),
					data.getDeviceGroup(), data.getReadingType(),
					new Random().nextDouble()*(data.getMaxReading().doubleValue() - data.getMinReading().doubleValue()) + data.getMinReading());
	}



	private void initalDataSetUp() {
		log.info("Starting client ");
		iotDataReactiveRepository.deleteAll()
		.thenMany(Flux.fromIterable(data()))
		.flatMap(iotDataReactiveRepository::save)
		.thenMany(iotDataReactiveRepository.findAll())
		.subscribe((item -> {
			log.info("Iotdata inserted from CommandLineRunner [{}] ",item);
			try {
				dataSetUpforCappedCollection(item);
			} catch (PulsarClientException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}));                
	}
}
