package com.anurag.iop.data.api.exception;

public class GetGeoLocationException extends Exception {
    public GetGeoLocationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public GetGeoLocationException(final String message) {
        super(message);
    }
}
