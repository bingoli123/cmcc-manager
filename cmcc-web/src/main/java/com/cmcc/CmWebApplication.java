package com.cmcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class CmWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmWebApplication.class, args);
	}
}
