package com.neeraj.preperation.spring.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoogleCredentialsProperties {

    @JsonIgnore
    public List<String> scopes;
    @JsonProperty("project_id")
    String projectId;
    String credentialsJson;
}
