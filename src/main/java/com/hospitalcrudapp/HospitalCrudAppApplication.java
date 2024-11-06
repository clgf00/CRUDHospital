package com.hospitalcrudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hospitalcrudapp")

public class HospitalCrudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalCrudAppApplication.class, args);
	}

}
