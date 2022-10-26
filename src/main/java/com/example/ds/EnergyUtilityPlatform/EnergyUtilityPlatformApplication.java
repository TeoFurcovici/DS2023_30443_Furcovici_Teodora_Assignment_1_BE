package com.example.ds.EnergyUtilityPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EnergyUtilityPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyUtilityPlatformApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return  new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE")
						.allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}
}
