package br.com.myhomefinances.service;

import java.net.URI;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.myhomefinances.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendConfirmationEmail(Usuario usuario) {
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromUsuario(usuario);
		sendEmail(simpleMailMessage);
	}

	@Override
	public void sendConfirmationHtmlEmail(Usuario usuario) {
		try {
			MimeMessage mimeMessage = prepareMimeMessageFromUsuario(usuario);
			sendHtmlEmail(mimeMessage);
		} catch (MessagingException e) {
			sendConfirmationEmail(usuario);
		}
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromUsuario(Usuario usuario) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(usuario.getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("MyHomeFinances - Teste");
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText("E-mail de teste do app MyHomeFinances");

		return simpleMailMessage;
	}

	protected MimeMessage prepareMimeMessageFromUsuario(Usuario usuario) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setTo(usuario.getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("MyHomeFinances - Teste");
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplateUsuario(usuario), true);

		return mimeMessage;
	}

	protected String htmlFromTemplateUsuario(Usuario usuario) {
		Context context = new Context();
		context.setVariable("usuario", usuario);

		return templateEngine.process("email/confirmacaoEmail", context);
	}

	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPassword) {
		SimpleMailMessage simpleMailMessage = prepareNewPasswordEmail(usuario, newPassword);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPassword) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(usuario.getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("Solicitação de nova senha");
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText("Nova senha: " + newPassword);

		return simpleMailMessage;
	}

	@Override
	public void sendResetTokenEmail(Usuario usuario) {
		SimpleMailMessage simpleMailMessage = prepareResetTokenEmail(usuario);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareResetTokenEmail(Usuario usuario) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		URI appUri = ServletUriComponentsBuilder.fromCurrentServletMapping()
				.path("/auth/resetPassword").query("token={token}").buildAndExpand(usuario.getResetToken()).toUri();

		simpleMailMessage.setTo(usuario.getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("Solicitação de nova senha");
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText("Para resetar sua senha, clique no link: " + appUri);

		return simpleMailMessage;
	}
}
