package com.anurag.iot.data.consumer.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class StoreConfiguration {

	@Autowired
	Environment env;

//	@Bean
//	public MongoDatabaseFactory mongoDbFactory() {
//		return  new SimpleMongoClientDatabaseFactory(env.getProperty("spring.data.mongodb.uri"));
//
//	}

	/*@Bean
	public MongoOperations mongoOperations(){
		return new MongoTemplate(mongoDbFactory());
	}*/

	@Bean	
	public PulsarClient pulsarClient() throws PulsarClientException {
		return PulsarClient.builder().serviceUrl(env.getProperty("pulsar.service-url"))			
				.build();
	}

}
