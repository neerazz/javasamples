package com.neeraj.preperation.spring.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on:  Jun 02, 2021
 * Ref: http://dolszewski.com/spring/multiple-ttl-caches-in-spring-boot/
 * https://www.javadevjournal.com/spring/spring-caching/
 */

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    @Profile("local")
    public CacheManager localCacheManager() {
        CaffeineCache searchByFirstName = buildCache("searchByFirstName", 60);
        CaffeineCache searchByFirstNameAndLastName = buildCache("searchByFirstNameAndLastName", 30);
        CaffeineCache findById = buildCache("findById", 60);
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(searchByFirstName, searchByFirstNameAndLastName, findById));
        return manager;
    }

    private CaffeineCache buildCache(String name, int minutesToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
                .maximumSize(100)
                .ticker(Ticker.systemTicker())
                .build());
    }
}
