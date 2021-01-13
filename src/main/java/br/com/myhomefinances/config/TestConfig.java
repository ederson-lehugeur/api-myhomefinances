package br.com.myhomefinances.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.myhomefinances.service.DBService;
import br.com.myhomefinances.service.EmailService;
import br.com.myhomefinances.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();

		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
