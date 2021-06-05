package com.anurag.iot.data.api.filter;

import com.anurag.iop.data.api.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static com.anurag.iop.data.api.constants.MetricsAPIConstants.DATE_TIME_FORMAT;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
public class MetricHandlerFilterFunction
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> next) {
        if(!serverRequest.path().contains("swagger")){
            Mono<ServerResponse> BAD_REQUEST = validateInputParameters(serverRequest);
            if (BAD_REQUEST != null) return BAD_REQUEST;
        }

        return next.handle(serverRequest);
    }


    private Mono<ServerResponse> validateInputParameters(ServerRequest serverRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        Optional<String> sensorOpt = serverRequest.queryParam("sensor");
        Optional<String> sensorgrpOpt = serverRequest.queryParam("sensorgrp");
        Optional<String> startDt=  serverRequest.queryParam("startdate");
        Optional<String> endDt =  serverRequest.queryParam("enddate");
        Date startDate= new Date(),endDate = new Date();


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
}
