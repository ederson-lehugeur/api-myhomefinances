package br.com.myhomefinances.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.myhomefinances.service.email.EmailService;
import br.com.myhomefinances.service.email.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
