package com.anurag.iot.data.api.constants;

import java.time.format.DateTimeFormatter;

public class MetricsAPIConstants {

    public static final String MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME = "/v1/getmaxbysensoranddatetimebetween";
    public static final String MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME = "/v1/getmaxbysensorgroupanddatetimebetween";
    public static final String MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME = "/v1/getminbysensoranddatetimebetween";
    public static final String MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME = "/v1/getminbysensorgroupanddatetimebetween";
    public static final String MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME = "/v1/getmedianbysensoranddatetimebetween";
    public static final String MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME = "/v1/getmedianbysensorgroupanddatetimebetween";
    public static final String AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME = "/v1/getaveragebysensoranddatetimebetween";
    public static final String AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME = "/v1/getaveragebysensorgroupanddatetimebetween";
    public static  final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public  static final String NOT_FOUND = "not found";
    public static final String ERROR_RAISED = "error raised";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

}
