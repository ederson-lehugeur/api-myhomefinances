package br.com.myhomefinances.service;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.PerfilRepository;
import br.com.myhomefinances.repository.SaldoRepository;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceTest {

	private static boolean initialized = false;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private SaldoRepository saldoRepository;

	@BeforeEach
	void init() {
		if (!initialized) {
			Perfil perfil1 = new Perfil(null, "ROLE_ADMIN");
			Perfil perfil2 = new Perfil(null, "ROLE_CLIENTE");

			perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));

			initialized = true;
		}
	}

	@Test
	void testarPerfilDeSomenteClienteNaCriacaoDeUsuario_Sucesso() {
		Usuario usuario = new Usuario(null, "Alan", "Turing", "alanturing@gmail.com", "$2a$10$VsLSsK65i7UVSW3c/SS9Vexv.sdizQTQHQgyrQ5BuD4gPKwfo94Bi");

		Usuario novoUsuario = usuarioService.insert(usuario);

		Assertions.assertEquals(1, novoUsuario.getPerfis().size());
		Assertions.assertEquals("ROLE_CLIENTE", novoUsuario.getPerfis().get(0).getNome());
	}

	@Test
	void testarContaComSaldoZeroNaCriacaoDeUsuario_Sucesso() {
		Usuario usuario = new Usuario(null, "Alan", "Turing", "alanturing@gmail.com", "$2a$10$VsLSsK65i7UVSW3c/SS9Vexv.sdizQTQHQgyrQ5BuD4gPKwfo94Bi");

		Usuario novoUsuario = usuarioService.insert(usuario);

		Saldo saldo = saldoRepository.findByUsuarioId(novoUsuario.getId()).get();

		Assertions.assertEquals(0.0D, saldo.getSaldo());
	}

}
