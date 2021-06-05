package com.anurag.iot.client.repository;

import com.anurag.iot.client.data.IotDataMaster;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IotDataReactiveRepository extends ReactiveMongoRepository<IotDataMaster,String> {

//    Mono<IotData> findByDescription(String description);
}
