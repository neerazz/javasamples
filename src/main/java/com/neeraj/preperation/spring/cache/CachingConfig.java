package com.neeraj.preperation.spring.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://www.javadevjournal.com/spring/spring-caching/
 */

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    @Profile("local")
    public CacheManager localCacheManager() {
        ConcurrentMapCacheManager concurrentMapCacheManager =
                new ConcurrentMapCacheManager(
                        "searchByFirstName",
                        "searchByFirstNameAndLastName",
                        "findById");
        concurrentMapCacheManager.setAllowNullValues(true);
        return concurrentMapCacheManager;
    }
}
