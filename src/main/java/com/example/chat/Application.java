package com.example.chat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	public class JsonConfigurer {

		@Bean
		@Primary
		public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
			ObjectMapper objectMapper = builder.build();
			objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
			objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
			return objectMapper;
		}

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedOrigins("*")
						.allowedHeaders("*")
						.exposedHeaders("Authorization");
			}
		};
	}
}
