package com.blueoptima.work.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.blue.optima.work.sample.service")
@ComponentScan("com.blue.optima.work.sample.controller")
@SpringBootApplication
public class NotesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesManagerApplication.class, args);
	}

}
