package com.inventory.config.inventoryconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class InventoryConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryConfigServerApplication.class, args);
	}

}
