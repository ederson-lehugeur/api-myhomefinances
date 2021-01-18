package br.com.myhomefinances.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.service.email.EmailService;
import br.com.myhomefinances.service.exception.IncorrectPasswordException;
import br.com.myhomefinances.service.exception.TokenExpiredException;

@Service
public class AuthService {

	@Value("${jwt.expirationResetToken}")
	private Long expirationResetToken;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailService emailService;

	public void forgotPassword(String email) {
		Usuario usuario = usuarioService.findByEmail(email);

		usuario.setResetToken(UUID.randomUUID().toString());
		usuario.setTokenExpirationDatetime(LocalDateTime.now().plusSeconds(expirationResetToken / 1000));

		usuarioService.updateUsuario(usuario);

		emailService.sendResetTokenEmail(usuario);
	}

	public void resetPassword(String token, String senha, String confirmacaoSenha) {
		if (!senha.equals(confirmacaoSenha)) {
			throw new IncorrectPasswordException("As senhas não conferem");
		}

		Usuario usuario = usuarioService.findByResetToken(token);

		LocalDateTime now = LocalDateTime.now();

		if (now.isAfter(usuario.getTokenExpirationDatetime())) {
			throw new TokenExpiredException("Token expirado");
		}

		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		usuario.setResetToken(null);
		usuario.setTokenExpirationDatetime(null);

		usuarioService.updateUsuario(usuario);
	}

}
