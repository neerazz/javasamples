package com.neeraj.preperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JavaPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPrepApplication.class, args);
	}

}
