package com.siva.bank_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BankApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}

	@Override 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(BankApiApplication.class);
	}

}
