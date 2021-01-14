package br.com.myhomefinances;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class MyhomefinancesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyhomefinancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {}

	@PostConstruct
    public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));	// "GMT-3"
    }

}
