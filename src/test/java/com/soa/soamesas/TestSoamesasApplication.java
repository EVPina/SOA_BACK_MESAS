package com.soa.soamesas;

import org.springframework.boot.SpringApplication;

public class TestSoamesasApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoamesasApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
