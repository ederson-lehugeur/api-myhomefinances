package br.com.myhomefinances.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.myhomefinances.services.EmailService;
import br.com.myhomefinances.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
