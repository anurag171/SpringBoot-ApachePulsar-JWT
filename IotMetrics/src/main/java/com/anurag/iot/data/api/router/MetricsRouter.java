package com.anurag.iot.data.api.router;

import com.anurag.iop.data.api.filter.MetricHandlerFilterFunction;
import com.anurag.iop.data.api.handler.MetricsHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.anurag.iop.data.api.constants.MetricsAPIConstants.MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static com.anurag.iop.data.api.constants.MetricsAPIConstants.*;

@Configuration
@Slf4j
public class MetricsRouter {

    @Autowired
    MetricHandlerFilterFunction metricHandlerFilterFunction;


    @RouterOperations({ @RouterOperation(path = MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMaxBySensorAndBetweenDateTime",
            operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Max Reading for Sensor" },
                    parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name",required = true),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                            @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                            @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMaxBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor group between specific time", tags = { "Api for Max Reading for Sensor Group" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Group Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMinBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Min Reading for Sensor" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMinBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Min Reading for Sensor Group" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getAverageBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Average Reading for Sensor" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getAverageBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Average Reading for Sensor Group" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMedianBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Median Reading for Sensor" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") })),
            @RouterOperation(path = MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMedianBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time", tags = { "Api for Median Reading for Sensor Group" },
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name"),@Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss"),@Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss") },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") }))
    })
    @Bean
    public RouterFunction<ServerResponse> metricsRoute(MetricsHandler metricsHandler){

        return RouterFunctions
                .route(GET(MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMaxBySensorAndBetweenDateTime)
                .andRoute(GET(MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMaxBySensorGroupAndBetweenDateTime)
                .andRoute(GET(MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMinBySensorAndBetweenDateTime)
                .andRoute(GET(MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMinBySensorGroupAndBetweenDateTime)
                .andRoute(GET(MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMedianBySensorAndBetweenDateTime)
                .andRoute(GET(MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMedianBySensorGroupAndBetweenDateTime)
                .andRoute(GET(AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getAverageBySensorAndBetweenDateTime)
                .andRoute(GET(AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getAverageBySensorGroupAndBetweenDateTime)
                .andRoute(RequestPredicates.path("/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config"),metricsHandler::ok)
                .filter(metricHandlerFilterFunction);
    }
}
