package com.anurag.iot.data.api.repository;

import com.anurag.iop.data.api.model.IotDataModel;
import com.anurag.iop.data.api.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Date;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@Slf4j
public class MetricsDao {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Flux<Response> getMaxBySensorAndBetweenDateTime(String sensor, Date startDate, Date endDate){

        Aggregation agg = newAggregation(
                match(Criteria.where("deviceName").is(sensor).and("date").gte(startDate).lt(endDate)),
                group("deviceName").max("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );

        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }

    public Flux<Response> getMaxBySensorGroupAndBetweenDateTime(String sensorGroup, Date startDate, Date endDate){

        Aggregation agg = newAggregation(
                match(Criteria.where("deviceGroup").is(sensorGroup).and("date").gte(startDate).lt(endDate)),
                group("deviceGroup").max("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );


        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }


    public Flux<Response> getMinBySensorGroupAndBetweenDateTime(String sensorGroup, Date startDate, Date endDate){
        Aggregation agg = newAggregation(
                match(Criteria.where("deviceGroup").is(sensorGroup).and("date").gte(startDate).lt(endDate)),
                group("deviceGroup").min("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );

        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }

    public Flux<Response> getMinBySensorAndBetweenDateTime(String sensor, Date startDate, Date endDate){

        Aggregation agg = newAggregation(
                match(Criteria.where("deviceName").is(sensor).and("date").gte(startDate).lt(endDate)),
                group("deviceName").min("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );

        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }

    public Flux<Response> getMedianBySensorGroupAndBetweenDateTime(String sensorGroup, Date startDate, Date endDate){

         MatchOperation matchOperation = match(Criteria.where("deviceGroup").is(sensorGroup).and("date").gte(startDate).lt(endDate));
        GroupOperation countByBookOwner = group("deviceGroup").count().as("nbReading");

        SortOperation sortByCount = sort(Sort.Direction.ASC, "nbReading");

        GroupOperation putInArray = group().push("nbReading").as("nbReadingArray");

        ProjectionOperation getSizeOfArray = project("nbReadingArray").and("nbReadingArray").size().as("size");

        ProjectionOperation divideSizeByTwo = project("nbReadingArray").and("size").divide(2).as("middleFloat");

        ProjectionOperation getIntValueOfDivisionForBornLeft = project("middleFloat", "nbReadingArray").and("middleFloat")
                .project("trunc").as("beginMiddle");

        ProjectionOperation add1ToBornLeftToGetBornRight = project("beginMiddle", "middleFloat", "nbReadingArray")
                .and("beginMiddle").project("add", 1).as("endMiddle");

        ProjectionOperation arrayElementAt = project("beginMiddle", "endMiddle", "middleFloat", "nbReadingArray")
                .and("nbReadingArray").project("arrayElemAt", "$beginMiddle").as("beginValue").and("nbReadingArray")
                .project("arrayElemAt", "$endMiddle").as("endValue");

        ProjectionOperation averageForMedian = project("beginMiddle", "endMiddle", "middleFloat", "nbReadingArray",
                "beginValue", "endValue").and("beginValue").project("avg", "$endValue").as("reading");


        Aggregation aggregation = newAggregation(matchOperation,countByBookOwner, sortByCount, putInArray, getSizeOfArray,
                divideSizeByTwo, getIntValueOfDivisionForBornLeft, add1ToBornLeftToGetBornRight, arrayElementAt,
                averageForMedian);

        long time = System.currentTimeMillis();
        return reactiveMongoTemplate.aggregate(aggregation, IotDataModel.class,
                Response.class).log();
    }

    public Flux<Response> getMedianBySensorAndBetweenDateTime(String sensor, Date startDate, Date endDate){

        MatchOperation matchOperation = match(Criteria.where("deviceName").is(sensor).and("date").gte(startDate).lt(endDate));

        GroupOperation countByBookOwner = group("deviceName").count().as("nbReading");

        SortOperation sortByCount = sort(Sort.Direction.ASC, "nbReading");

        GroupOperation putInArray = group().push("nbReading").as("nbReadingArray");

        ProjectionOperation getSizeOfArray = project("nbReadingArray").and("nbReadingArray").size().as("size");

        ProjectionOperation divideSizeByTwo = project("nbReadingArray").and("size").divide(2).as("middleFloat");

        ProjectionOperation getIntValueOfDivisionForBornLeft = project("middleFloat", "nbReadingArray").and("middleFloat")
                .project("trunc").as("beginMiddle");

        ProjectionOperation add1ToBornLeftToGetBornRight = project("beginMiddle", "middleFloat", "nbReadingArray")
                .and("beginMiddle").project("add", 1).as("endMiddle");

        ProjectionOperation arrayElementAt = project("beginMiddle", "endMiddle", "middleFloat", "nbReadingArray")
                .and("nbReadingArray").project("arrayElemAt", "$beginMiddle").as("beginValue").and("nbReadingArray")
                .project("arrayElemAt", "$endMiddle").as("endValue");

        ProjectionOperation averageForMedian = project("beginMiddle", "endMiddle", "middleFloat", "nbReadingArray",
                "beginValue", "endValue").and("beginValue").project("avg", "$endValue").as("reading");

        ProjectionOperation finalProjection = project("reading").and("product").previousOperation();

        Aggregation aggregation = newAggregation(matchOperation,countByBookOwner, sortByCount, putInArray, getSizeOfArray,
                divideSizeByTwo, getIntValueOfDivisionForBornLeft, add1ToBornLeftToGetBornRight, arrayElementAt,
                averageForMedian,finalProjection);

        long time = System.currentTimeMillis();
        return reactiveMongoTemplate.aggregate(aggregation, IotDataModel.class,
                Response.class).log();
    }

    public Flux<Response> getAverageBySensorGroupAndBetweenDateTime(String sensorGroup, Date startDate, Date endDate){
        Aggregation agg = newAggregation(
                match(Criteria.where("deviceGroup").is(sensorGroup).and("date").gte(startDate).lt(endDate)),
                group("deviceName").avg("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );

        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }

    public Flux<Response> getAverageBySensorAndBetweenDateTime(String sensor, Date startDate, Date endDate){
        Aggregation agg = newAggregation(
                match(Criteria.where("deviceName").is(sensor).and("date").gte(startDate).lt(endDate)),
                group("deviceName").avg("reading").as("reading"),
                project("reading").and("product").previousOperation()
        );

        return reactiveMongoTemplate.aggregate(agg,IotDataModel.class, Response.class).log();
    }


}
