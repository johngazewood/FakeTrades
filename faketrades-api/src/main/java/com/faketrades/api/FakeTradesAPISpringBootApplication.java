package com.faketrades.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FakeTradesAPISpringBootApplication {

	private static final Logger logger = LogManager.getLogger(FakeTradesAPISpringBootApplication.class);

	public static void main(String[] args) {
		logger.info("--------------------------------------------------------");
		logger.info("Starting Spring Application.");
		SpringApplication.run(FakeTradesAPISpringBootApplication.class, args);
	}

}
