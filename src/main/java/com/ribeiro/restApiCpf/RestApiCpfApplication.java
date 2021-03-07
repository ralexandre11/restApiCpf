package com.ribeiro.restApiCpf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableKafka
@SpringBootApplication
//@EnableWebMvc
public class RestApiCpfApplication implements WebMvcConfigurer {


	@Override
	public void addCorsMappings( CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RestApiCpfApplication.class, args);
	}

}
