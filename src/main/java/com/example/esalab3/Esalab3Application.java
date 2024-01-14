package com.example.esalab3;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@SpringBootApplication(scanBasePackages="com.example")
@EnableJpaRepositories(basePackages = "com.example.esalab3.repository") @EntityScan(basePackages = "com.example.esalab3.entity")
@EnableRabbit
public class Esalab3Application {
	public static void main(String[] args) {
		SpringApplication.run(Esalab3Application.class, args);
	}
}
