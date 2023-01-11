package com.neeraj.preperation.spring.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;

import static com.neeraj.preperation.spring.exceptions.JSONConversionException.printError;

@Slf4j
public class InvalidAPICallException extends RuntimeException {
    public InvalidAPICallException(String url, Object body, Exception exception) {
        log.error("Error while Making an API call to URL : {} with Body : {}.", url, body);
        printError(exception);
    }

    public InvalidAPICallException(HttpRequest httpRequest, Exception e) {
        log.error("Error Making an {} API Call to : {}", httpRequest.method(), httpRequest.uri());
        log.error("Error Message : {}", e.getMessage());
        e.printStackTrace();
    }
}
