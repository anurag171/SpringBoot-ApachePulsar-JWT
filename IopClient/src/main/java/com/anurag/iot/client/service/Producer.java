package com.anurag.iot.client.service;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anurag.iot.client.data.IotData;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Producer {

	@Autowired
	private PulsarTemplate<IotData> producer;
	
	@Value("${data-topic}")
	private String topic;

	public void send(IotData msg) throws PulsarClientException {
		log.info("Sending data {}", msg.toString());
		producer.sendAsync(topic, msg);
	}

}
