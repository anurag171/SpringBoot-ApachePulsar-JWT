package com.anurag.iop.data.discovery.MetricDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MetricDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricDiscoveryApplication.class, args);
	}

}
