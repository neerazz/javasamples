package com.neeraj.preperation.spring.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://www.javadevjournal.com/spring/spring-cache-custom-keygenerator/
 */

@Slf4j
public class CustomFirstLastNameKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
//        This is method will be called to generate the key during the caching.
        String key = String.format("[%s.%s] Fields: %s", target.getClass().getSimpleName(), method.getName(), Arrays.toString(params));
        System.out.println("Creating key for the method Invocation : " + method.getName() + " in class : " + target.getClass().getSimpleName());
        return key;
    }
}
