package com.faketrades.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:kinesis.properties")
public class FakeTradesProcessorStartup {

	public static void main(String[] args) {
		SpringApplication.run(FakeTradesProcessorStartup.class, args);
	}


}
