package com.anurag.iot.data.api.router;

import com.anurag.iot.data.api.filter.MetricHandlerFilterFunction;
import com.anurag.iot.data.api.handler.MetricsHandler;
import com.anurag.iot.data.api.constants.MetricsAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static com.anurag.iot.data.api.constants.MetricsAPIConstants.*;

@Configuration
@Slf4j
public class MetricsRouter {

    @Autowired
    MetricHandlerFilterFunction metricHandlerFilterFunction;


    @RouterOperations({ @RouterOperation(path = MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMaxBySensorAndBetweenDateTime",
            operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                    tags = { "Api for Max Reading for Sensor" },
                    security = {@SecurityRequirement(name = "bearer-key")},
                    parameters = {@Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name",required = true),
                            @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                            @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                 @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                 @ApiResponse(responseCode = "404", description = "invalid request"),
                                @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMaxBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor group between specific time",
                            tags = { "Api for Max Reading for Sensor Group" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = {@Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Group Name",required = true),
                                    @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                    @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMinBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Min Reading for Sensor" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request") ,
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMinBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Min Reading for Sensor Group" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name",required = true),
                                         @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                        @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getAverageBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Average Reading for Sensor" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getAverageBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Average Reading for Sensor Group" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMedianBySensorAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Median Reading for Sensor" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensor", description = "Sensor Name",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")})),
            @RouterOperation(path = MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME, beanClass = MetricsHandler.class, beanMethod = "getMedianBySensorGroupAndBetweenDateTime",
                    operation = @Operation(operationId = "getMaxBySensorAndBetweenDateTime", summary = "Find Max value of reading for sensor between specific time",
                            tags = { "Api for Median Reading for Sensor Group" },
                            security = {@SecurityRequirement(name = "bearer-key")},
                            parameters = { @Parameter(in = ParameterIn.QUERY, name = "sensorgrp", description = "Sensor Name",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "startdate", description = "Starting Date Time in yyyy-MM-dd HH:mm:ss",required = true),
                                           @Parameter(in = ParameterIn.QUERY, name = "enddate", description = "End Date Time in yyyy-MM-dd HH:mm:ss",required = true) },
                            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MetricsHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "invalid input parameters"),
                                    @ApiResponse(responseCode = "404", description = "invalid request"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized")}))
    })
    @Bean
    public RouterFunction<ServerResponse> metricsRoute(MetricsHandler metricsHandler){

        return RouterFunctions
                .route(GET(MetricsAPIConstants.MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMaxBySensorAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMaxBySensorGroupAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                ,metricsHandler::getMinBySensorAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMinBySensorGroupAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMedianBySensorAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getMedianBySensorGroupAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getAverageBySensorAndBetweenDateTime)
                .andRoute(GET(MetricsAPIConstants.AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).and(accept(APPLICATION_JSON))
                        ,metricsHandler::getAverageBySensorGroupAndBetweenDateTime)
                .andRoute(RequestPredicates.path("/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config"),metricsHandler::ok)
                .filter(metricHandlerFilterFunction);
    }
}
