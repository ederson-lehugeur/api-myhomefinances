package br.com.myhomefinances.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Usuario;

@Service
public class AuthService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		Usuario usuario = usuarioService.findByEmail(email);

		String newPass = newPassword();

		usuario.setSenha(bCryptPasswordEncoder.encode(newPass));

		usuarioService.updatePasswordForgot(usuario);

		emailService.sendNewPasswordEmail(usuario, newPass);
	}

	private String newPassword() {
		char[] vet = new char[16];
		for (int i = 0; i < 16; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);

		if (opt == 0) {	// 0-9
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // A-Z
			return (char) (rand.nextInt(26) + 65);
		} else { // a-z
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
