package com.stringauto;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@SpringBootApplication
public class HadronsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HadronsApplication.class, args);
	}
}
