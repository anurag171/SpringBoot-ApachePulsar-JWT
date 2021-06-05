package com.anurag.iot.data.consumer.dao;

import com.anurag.iot.data.consumer.model.IotDataModel;
import com.anurag.iot.data.consumer.model.IotErrorDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoDbOperations {
	
	@Autowired
	MongoTemplate reactiveMongoTemplate;

	public void saveValidData(IotDataModel msg) {
		reactiveMongoTemplate.save(msg);
	}

	public void saveErrorData(IotErrorDataModel msg) {
		reactiveMongoTemplate.save(msg);
		
	}

}
