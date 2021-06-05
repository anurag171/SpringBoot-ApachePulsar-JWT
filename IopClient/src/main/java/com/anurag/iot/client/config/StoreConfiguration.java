package com.anurag.iot.client.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.anurag.iot.client.data.IotData;

import io.github.majusko.pulsar.producer.ProducerFactory;

@Configuration
public class StoreConfiguration {
	
	@Autowired
	Environment env;

	@Bean
	public ProducerFactory producerFactory() {
		return new ProducerFactory()
				.addProducer(env.getProperty("data-topic"), IotData.class);
	}
	
	
	@Bean	
	public PulsarClient pulsarClient() throws PulsarClientException {
		return PulsarClient.builder().serviceUrl(env.getProperty("pulsar.service-url"))			
				.build();
	}
	

}
