package com.anurag.iot.data.consumer.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anurag.iot.data.consumer.dao.MongoDbOperations;
import com.anurag.iot.data.consumer.data.IotData;
import com.anurag.iot.data.consumer.model.IotDataModel;
import com.anurag.iot.data.consumer.model.IotErrorDataModel;
import com.google.gson.Gson;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataConsumer {
	
	Consumer<byte[]> consumer;

	@Autowired
	MongoDbOperations dbOperations;
	
	@Autowired
	PulsarClient pulsarClient;
	
	@Value("${data-topic}")
	private String topic;
	
	@PostConstruct
	public void init() throws PulsarClientException {
		Consumer<byte[]> consumer = pulsarClient.newConsumer()
		        .topic(topic)
		        .subscriptionName("my-subscription-name")
		        .subscribe();

		while (true) {
		     Message<byte[]> message = consumer.receive();
		     String strMsg = new String(message.getValue());
		     log.info("Got message: " + strMsg);
		     consumer.acknowledge(message);
		     Gson gson = new Gson();
		     IotData data = gson.fromJson(strMsg, IotData.class);
		     validateData(data);		     
		}
	}
	
	
	

	@PulsarConsumer(topic="iot-topic", clazz=IotData.class)
	void consume(IotData msg) throws PulsarClientException { 
		//pulsarClient.r
		log.info("Recieved data {}", msg.toString());
		validateData(msg);
	}

	private void validateData(IotData msg) {
		
		List<String> errorList = new ArrayList<>();
		
		boolean isValidData = validateMsg(msg,errorList);

		if(isValidData) {
			IotDataModel model = new IotDataModel();
			BeanUtils.copyProperties(msg, model);
			model.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			storeValidDataToDb(model);			
		}else {
			IotErrorDataModel model = new IotErrorDataModel();
			BeanUtils.copyProperties(msg, model);
			model.setError(errorList);
			model.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			storeInValidDataToDb(model);		
		}


	}

	private boolean validateMsg(IotData msg, List<String> errorList) {
		
		return true;
	}

	private void storeValidDataToDb(IotDataModel msg) {
		dbOperations.saveValidData(msg);
	}

	private void storeInValidDataToDb(IotErrorDataModel msg) {

		dbOperations.saveErrorData(msg);
	}

}
