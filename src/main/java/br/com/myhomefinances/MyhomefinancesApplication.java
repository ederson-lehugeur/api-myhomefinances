package br.com.myhomefinances;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyhomefinancesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyhomefinancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {}

}
