package com.kerro16.InventarioCasaBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kerro16.InventarioCasaBackend.repository")
public class InventarioCasaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioCasaBackendApplication.class, args);
	}

}
