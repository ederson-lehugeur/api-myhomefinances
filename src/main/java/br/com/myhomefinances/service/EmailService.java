package br.com.myhomefinances.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.myhomefinances.domain.Usuario;

public interface EmailService {

	void sendConfirmationEmail(Usuario usuario);
	void sendEmail(SimpleMailMessage simpleMailMessage);
	void sendConfirmationHtmlEmail(Usuario usuario);
	void sendHtmlEmail(MimeMessage mimeMessage);
	void sendNewPasswordEmail(Usuario usuario, String newPassword);
	void sendResetTokenEmail(Usuario usuario);

}
