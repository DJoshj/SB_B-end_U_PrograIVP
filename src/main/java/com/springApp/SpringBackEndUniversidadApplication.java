package com.springApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringBackEndUniversidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackEndUniversidadApplication.class, args);
	}

}
