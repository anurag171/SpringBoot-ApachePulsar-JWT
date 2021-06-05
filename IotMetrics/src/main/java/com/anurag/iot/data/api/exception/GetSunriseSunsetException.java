package com.anurag.iot.data.api.exception;

public class GetSunriseSunsetException extends Exception {

    public GetSunriseSunsetException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public GetSunriseSunsetException(final String message) {
        super(message);
    }
}
