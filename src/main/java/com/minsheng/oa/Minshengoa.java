package com.minsheng.oa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Minshengoa {
	private static final Logger LOGGER = LoggerFactory.getLogger(Minshengoa.class);
	public static void main(String[] args) {
		SpringApplication.run(Minshengoa.class, args);
		LOGGER.info("Ueditor application started!!!");
	}



	}

