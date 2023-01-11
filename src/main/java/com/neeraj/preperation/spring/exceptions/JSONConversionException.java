package com.neeraj.preperation.spring.exceptions;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class JSONConversionException extends RuntimeException {

    public JSONConversionException(Object data, Exception exception) {
        super(exception);
        log.error("Error while Converting Object : {}, to JSON String.", data);
        printError(exception);
    }

    public <A> JSONConversionException(Object json, Exception exception, Class<A> clazz) {
        super(exception);
        log.error("Error while Converting Json : {}, to class : {}.", json, clazz);
        printError(exception);
    }

    public <A> JSONConversionException(Object json, Exception exception, TypeReference<A> typeRef) {
        super(exception);
        log.error("Error while Converting Json : {}, to Type : {}.", json, typeRef.getType());
        printError(exception);
    }

    public <A> JSONConversionException(Object json, Exception exception, TypeReference<A> typeRef, String... fieldNames) {
        super(exception);
        log.error("Error while Converting Json : {}, with nested values : {} to Type : {}.", json, Arrays.toString(fieldNames), typeRef.getType());
        printError(exception);
    }

    public static void printError(Exception exception) {
        log.error("Error Message: {}", exception.getMessage());
    }
}
