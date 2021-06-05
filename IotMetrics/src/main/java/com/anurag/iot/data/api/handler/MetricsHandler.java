package com.anurag.iot.data.api.handler;

import com.anurag.iop.data.api.exception.PathNotFoundException;
import com.anurag.iop.data.api.model.ErrorResponse;
import com.anurag.iop.data.api.model.Response;
import com.anurag.iop.data.api.repository.MetricsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.Date;
import static com.anurag.iop.data.api.constants.MetricsAPIConstants.*;

@Component
@Slf4j
public class MetricsHandler implements AppHandler{

    @Autowired
    MetricsDao metricsDao;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getMaxBySensorAndBetweenDateTime(ServerRequest serverRequest)  {
            String   sensor = serverRequest.queryParam("sensor").get();
            Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
            Date endDate =  formatDate(serverRequest.queryParam("enddate").get());

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(metricsDao.getMaxBySensorAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                    .onErrorResume(this::throwableError);
    }

    public Mono<ServerResponse> getMaxBySensorGroupAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensorgrp").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getMaxBySensorGroupAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);
    }

    public Mono<ServerResponse> getMinBySensorGroupAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensorgrp").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());


        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getMinBySensorGroupAndBetweenDateTime(sensor,startDate,endDate), Response.class);

    }

    public Mono<ServerResponse> getMinBySensorAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensor").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getMinBySensorAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);

    }

    public Mono<ServerResponse> getMedianBySensorAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensor").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());


        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getMedianBySensorAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);

    }

    public Mono<ServerResponse> getMedianBySensorGroupAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensorgrp").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());


        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getMedianBySensorGroupAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);

    }

    public Mono<ServerResponse> getAverageBySensorGroupAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensorgrp").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getAverageBySensorGroupAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);

    }

    public Mono<ServerResponse> getAverageBySensorAndBetweenDateTime(ServerRequest serverRequest) {
        String   sensor = serverRequest.queryParam("sensor").get();
        Date startDate =  formatDate(serverRequest.queryParam("startdate").get());
        Date endDate =  formatDate(serverRequest.queryParam("enddate").get());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metricsDao.getAverageBySensorAndBetweenDateTime(sensor,startDate,endDate), Response.class)
                .onErrorResume(this::throwableError);

    }

    public Mono<ServerResponse> metricsEx(ServerRequest serverRequest){

        throw new RuntimeException("RuntimeException Occurred");
    }

    public Mono<ServerResponse> notFound(final ServerRequest request) {
        return Mono.just(new PathNotFoundException(NOT_FOUND)).transform(this::getResponse);
    }

    Mono<ServerResponse> throwableError(final Throwable error) {
        log.error(ERROR_RAISED, error);
        return Mono.just(error).transform(this::getResponse);
    }

    <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> monoError) {
        return monoError.transform(ThrowableTranslator::translate)
                .flatMap(translation -> ServerResponse
                        .status(translation.getHttpStatus())
                        .body(Mono.just(new ErrorResponse(translation.getMessage())), ErrorResponse.class));
    }

    public Mono<ServerResponse> ok(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

    /*public Mono<ServerResponse> getOneItem(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Item> itemMono = itemReactiveRepository.findById(id);

        return itemMono.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item)))
                .switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> createItem(ServerRequest serverRequest) {

        Mono<Item> itemTobeInserted = serverRequest.bodyToMono(Item.class);

        return itemTobeInserted.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(itemReactiveRepository.save(item), Item.class));

    }

    public Mono<ServerResponse> deleteItem(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Void> deleteItem = itemReactiveRepository.deleteById(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deleteItem, Void.class);
    }

    public Mono<ServerResponse> updateItem(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");

        Mono<Item> updatedItem = serverRequest.bodyToMono(Item.class)
                .flatMap((item) -> {

                    Mono<Item> itemMono = itemReactiveRepository.findById(id)
                            .flatMap(currentItem -> {
                                currentItem.setDescription(item.getDescription());
                                currentItem.setPrice(item.getPrice());
                                return itemReactiveRepository.save(currentItem);

                            });
                    return itemMono;
                });

        return updatedItem.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item)))
                .switchIfEmpty(notFound);


    }

    public Mono<ServerResponse> itemsStream(ServerRequest serverRequest){

        return  ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(itemReactiveCappedRepository.findItemsBy(), ItemCapped.class);
    }

    public Mono<ServerResponse> itemsEx(ServerRequest serverRequest){

        throw new RuntimeException("RuntimeException Occurred");
    }*/


}
