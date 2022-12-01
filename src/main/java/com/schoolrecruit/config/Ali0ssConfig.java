package com.schoolrecruit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alioss")
@Data
public class Ali0ssConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String roleArn;
    private String regionID;
    private String bucket;
}

