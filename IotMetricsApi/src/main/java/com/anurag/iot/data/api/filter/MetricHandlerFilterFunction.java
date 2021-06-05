package com.anurag.iot.data.api.filter;

import com.anurag.iot.data.api.model.ErrorResponse;
import com.anurag.iot.data.api.constants.MetricsAPIConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
public class MetricHandlerFilterFunction
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final String EMPTY = "Empty Value Received";

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> next) {
        if(!serverRequest.path().contains("swagger")){
            Mono<ServerResponse> BAD_REQUEST = validateInputParameters(serverRequest);
            if (BAD_REQUEST != null) return BAD_REQUEST;
        }

        return next.handle(serverRequest);
    }


    private Mono<ServerResponse> validateInputParameters(ServerRequest serverRequest) {
        log.info("Inside validateInputParameters");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MetricsAPIConstants.DATE_TIME_FORMAT);
        log.info("Inside validateInputParameters1");
        Optional<String> sensorOpt = serverRequest.queryParam("sensor");
        log.info("Inside validateInputParameters2");
        Optional<String> sensorgrpOpt = serverRequest.queryParam("sensorgrp");
        log.info("Inside validateInputParameters3");
        Optional<String> startDt=  serverRequest.queryParam("startdate");
        log.info("Inside validateInputParameters4");
        Optional<String> endDt =  serverRequest.queryParam("enddate");
        log.info("Inside validateInputParameters5");
       // Date startDate= new Date(),endDate = new Date();

        log.info("Inside validateInputParameters  Received params sensor [{}], sensorgrp [{}], startdate [{}], enddate [{}]"
                ,getOptValueElseEmpty(sensorOpt),getOptValueElseEmpty(sensorgrpOpt),getOptValueElseEmpty(startDt),getOptValueElseEmpty(endDt));


        String sensor = "";
        String sensorgrp = "";
        if(sensorOpt.isPresent()){
            sensor=   sensorOpt.get();
            if (sensor.trim().length()==0){
                return ServerResponse.status(BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).bodyValue(new ErrorResponse("invalid sensor name"));
            }
        }else if(sensorgrpOpt.isPresent()){
            sensorgrp=   sensorgrpOpt.get();
            if (sensorgrp.trim().length()==0){
                return ServerResponse.status(BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).bodyValue(new ErrorResponse("invalid sensor group anme"));
            }
        }

        if(startDt.isPresent()){
            try{
                LocalDateTime ldt = LocalDateTime.parse(startDt.get(),formatter);

            }catch(Exception ex){
                return ServerResponse.status(BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).bodyValue(new ErrorResponse("invalid start date"));
            }

        }
        if(endDt.isPresent()){
            try {
                LocalDateTime ldt = LocalDateTime.parse(endDt.get(), formatter);
            }catch (Exception ex){
                return ServerResponse.status(BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).bodyValue(new ErrorResponse("invalid end date"));
            }
        }
        return null;
    }

    private String getOptValueElseEmpty(Optional<String> optional){

        return  optional.isPresent()?optional.get():EMPTY;
    }
}
