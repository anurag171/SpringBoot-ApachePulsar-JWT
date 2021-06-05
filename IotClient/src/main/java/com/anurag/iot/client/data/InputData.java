package com.anurag.iot.client.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class InputData {

    private String deviceName;
    private String deviceGroup;
    private String readingType;
    private String maxReadingRange;
    private String minReadingRange;
}
