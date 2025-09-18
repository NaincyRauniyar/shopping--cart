package com.springcodework.dreamcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.springcodework.dreamcart.model")
@EnableJpaRepositories
public class DreamCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamCartApplication.class, args);
	}

}
