package com.neeraj.preperation.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("kafka")
public class KafkaProperties {

    private String bootstrapServers;
    private String schemaRegistryUrl;
    private String consumerGroupId;
    private String vmsAssetTopic;
    private String vmsVulnerabilityTopic;
    private String vmsVulnerabilityFindingTopic;
    private String vmsSecurityControlsTopic;
    private String vmsSecuritySubCategoriesTopic;
    private String vmsSecurityControlFindingsTopic;
    private String vmsIssuesTopic;
}
