package com.neeraj.preperation.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class DateUtil {

    static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * Parses out the given date into an actual java Date object.
     * The given date is expected to be in one of the ISO 8601 format, but will also take in date that use '/' instead of proper ISO 8601 '-'.
     *
     * @param date the date to pass
     * @return ZonedDateTime
     * @throws InvalidParameterSpecException
     */
    public static ZonedDateTime parseISO8601(String date) {
        try {

            return ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(ISO8601));
        } catch (Exception e) {
            log.error("There was an error converting {}, to {} format.", date, ISO8601);
            throw new RuntimeException(String.format("Exception when parsing date %s.", date));
        }
    }

    public static String getTimeInISO8601(ZonedDateTime dateTime) {
        return getStringTime(dateTime, ISO8601);
    }

    public static String getTimeInISO8601(LocalDateTime dateTime) {
        return getStringTime(dateTime.atZone(ZoneId.systemDefault()), ISO8601);
    }


    public static String getStringTimeInISO8601(Integer minusDays) {
        return getTimeInISO8601(ZonedDateTime.now().minusDays(minusDays));
    }

    public static String getCurrentTimeInISO8601() {
        return getTimeInISO8601(ZonedDateTime.now());
    }

    public static String getCurrentTime(String format) {
        var formatter = Optional.ofNullable(format).orElse(ISO8601);
        return ZonedDateTime.now().format(DateTimeFormatter.ofPattern(formatter));
    }

    public static String getStringTime(ZonedDateTime dataTime, String format) {
        return dataTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static long getUnixTime(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static long getUnixTime(Integer minusDays) {
        return getUnixTime(LocalDateTime.now().minusDays(minusDays));
    }
}
