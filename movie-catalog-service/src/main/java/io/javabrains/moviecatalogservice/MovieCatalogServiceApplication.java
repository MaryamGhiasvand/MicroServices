package io.javabrains.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	//since beans are singleton by default => it returns exactly one rest template for the whole app
	//this method executes just once => if any api needs it, it returns exactly the same one
	@Bean
	public RestTemplate getRrRestTemplate(){
		return new RestTemplate();
	}
}
