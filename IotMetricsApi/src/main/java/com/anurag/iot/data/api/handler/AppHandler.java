package com.anurag.iot.data.api.handler;

import com.anurag.iot.data.api.constants.MetricsAPIConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public interface AppHandler {

    default Date formatDate(String date){
        LocalDateTime ldt = LocalDateTime.parse(date, MetricsAPIConstants.formatter);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
