package com.spring.batch.sisan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SisanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisanApplication.class, args);
	}

}
