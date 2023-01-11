package com.neeraj.preperation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.neeraj.preperation.spring.properties.GoogleCredentialsProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class GCPUtils {

    GoogleCredentialsProperties properties;
    ServiceAccountCredentials accountCredentials;

    public GCPUtils(GoogleCredentialsProperties properties) {
        this.properties = properties;
        this.accountCredentials = getGoogleCredential(properties.getCredentialsJson());
    }

    public String getProperties() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(properties);
        return jsonString.replace("\\n", "\n");
    }

    public ServiceAccountCredentials getGoogleCredential(String credentialsStream) {
        log.debug("Creating Google credentials from Stream : {}", credentialsStream);
        try {
            var credentials = ServiceAccountCredentials.fromStream(new ByteArrayInputStream(credentialsStream.getBytes(StandardCharsets.UTF_8)));
            credentials.createScoped(properties.getScopes());
            log.debug("Credentials created successfully.");
            return credentials;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Unable to read and parse Google account Json Property");
            log.error("Error Details : {}", e.getMessage());
            throw new RuntimeException("Unable to read and parse Google account Json Property. Error Details : " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Unable to create Google credentials from Property Json.");
            log.error("Error Details : {}", e.getMessage());
            throw new RuntimeException("Unable to create Google credentials from Property Json. Error Details : " + e.getLocalizedMessage());
        }
    }

    public String generateAccessToken() {
        AccessToken accessToken = accountCredentials.getAccessToken();
        try {
            if (accessToken == null ||
                    System.currentTimeMillis() > accessToken.getExpirationTime().getTime()) {
                accessToken = accountCredentials.refreshAccessToken();
            }
            return accessToken.getTokenValue();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to to get Access token.");
        }
    }
}
