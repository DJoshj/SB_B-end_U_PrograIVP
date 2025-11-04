package com.springApp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//mapea las propiedades de configuración del archivo application.properties
@ConfigurationProperties(prefix = "app")
public class ExternalizedConfigurations {
    private String name;
    private String version;
    private String autor;
    private String language;
    private String country;
    private String supportEmail;
    private String baseUrl;


}
