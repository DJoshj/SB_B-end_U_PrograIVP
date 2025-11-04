package com.springApp.controllers;

import com.springApp.config.ExternalizedConfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class AppConfigController {
    private final ExternalizedConfigurations config;

    @Autowired
    public AppConfigController(ExternalizedConfigurations config) {
        this.config = config;
    }

    @GetMapping
    public ExternalizedConfigurations getAppConfig() {
        return config; // Devuelve JSON con todos los valores
    }

    @GetMapping("/autor")
    public String getAutor() {
        return config.getAutor();
    }

    @GetMapping("/version")
    public String getVersion() {
        return config.getVersion();
    }

    @GetMapping("/support-email")
    public String getSupportEmail() {
        return config.getSupportEmail();
    }
}
