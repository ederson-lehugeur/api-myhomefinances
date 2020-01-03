package br.com.myhomefinances.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.services.exception.IncorrectPasswordException;

@Service
public class AuthService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailService emailService;

	public void forgotPassword(String email) {
		Usuario usuario = usuarioService.findByEmail(email);

		usuario.setResetToken(UUID.randomUUID().toString());

		usuarioService.updatePasswordForgot(usuario);

		emailService.sendResetTokenEmail(usuario);
	}

	public void resetPassword(String token, String senha, String confirmacaoSenha) {
		if (!senha.equals(confirmacaoSenha)) {
			throw new IncorrectPasswordException("As senhas não conferem");
		}

		Usuario usuario = usuarioService.findByResetToken(token);

		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		usuario.setResetToken(null);

		usuarioService.updatePasswordForgot(usuario);
	}
}
