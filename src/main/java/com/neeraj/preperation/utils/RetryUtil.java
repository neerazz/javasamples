package com.neeraj.preperation.utils;

import com.wayfair.security.vms.aggregator.exceptions.InvalidAPICallException;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.function.Function;

@Slf4j
public class RetryUtil {

    static final int DEFAULT_WAIT_TIME_IN_SECONDS = 2;
    static final Random RANDOM = new Random();

    private RetryUtil() {
    }

    public static <I, O> O retryFunction(Function<I, O> callFunction, I input, int maxRetries) {
        return retryFunction(callFunction, input, maxRetries, DEFAULT_WAIT_TIME_IN_SECONDS);
    }

    public static <I, O> O retryFunction(Function<I, O> callFunction, I input, int maxRetries, int waitTimeInSeconds) {
        Exception exception = null;
        for (int retries = 1; retries <= maxRetries; retries++) {
            try {
                return callFunction.apply(input);
            } catch (Exception e) {
                exception = e;
                int waitDuration = RANDOM.nextInt(1, 20) * retries * waitTimeInSeconds * 60 * 1000;
                log.warn("There was an error while making an API call for {} time.", retries + 1);
                log.warn("API call will be retried again after {} seconds.", waitDuration);
                try {
                    Thread.sleep(waitDuration);
                } catch (Exception ex) {
                    log.error("Error while waiting to retry Function : {}", callFunction);
                }
            }
        }
        throw new InvalidAPICallException(callFunction.toString(), input, exception);
    }
}
