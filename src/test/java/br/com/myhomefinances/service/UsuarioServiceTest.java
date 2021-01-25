package br.com.myhomefinances.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import br.com.myhomefinances.domain.Usuario;

@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioServiceTest {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UsuarioService usuarioService;

	@Test
	void testarPerfilDeSomenteClienteNaCriacaoDeUsuario_Sucesso() {
		Usuario usuario = new Usuario(null, "Alan", "Turing", bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Usuario novoUsuario = usuarioService.insert(usuario);

		Assertions.assertEquals(1, novoUsuario.getPerfis().size());
		Assertions.assertEquals("ROLE_CLIENTE", novoUsuario.getPerfis().get(0).getNome());
	}

}
