package io.javabrains.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	//since beans are singleton by default => it returns exactly one rest template for the whole app
	//this method executes just once => if any api needs it, it returns exactly the same one
	//same as a builder
	@Bean
	@LoadBalanced //does service discovery in load balanced way
	//api gives the service name to restTemplate, to call the Eureka, get the server URl, and make the actual call
	public RestTemplate getRrRestTemplate(){
		return new RestTemplate();
	}

	//use weblient instead of RestTemplate
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
}
