package com.anurag.iot.client.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.anurag.iot.client.data.IotData;

public interface IotDataReactiveRepository extends ReactiveMongoRepository<IotData,String> {

//    Mono<IotData> findByDescription(String description);
}
