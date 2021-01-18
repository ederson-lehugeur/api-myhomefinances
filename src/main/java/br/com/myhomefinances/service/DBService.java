package br.com.myhomefinances.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.BancoRepository;
import br.com.myhomefinances.repository.CategoriaRepository;
import br.com.myhomefinances.repository.ContaRepository;
import br.com.myhomefinances.repository.ItemRepository;
import br.com.myhomefinances.repository.PerfilRepository;
import br.com.myhomefinances.repository.RegistroBancarioRepository;
import br.com.myhomefinances.repository.RegistroRepository;
import br.com.myhomefinances.repository.SaldoBancarioRepository;
import br.com.myhomefinances.repository.SaldoRepository;
import br.com.myhomefinances.repository.TipoContaRepository;
import br.com.myhomefinances.repository.TipoRegistroRepository;
import br.com.myhomefinances.repository.UsuarioRepository;

@Service
public class DBService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	TipoRegistroRepository tipoRegistroRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	RegistroRepository registroRepository;

	@Autowired
	SaldoRepository saldoRepository;

	@Autowired
	BancoRepository bancoRepository;

	@Autowired
	TipoContaRepository tipoContaRepository;

	@Autowired
	ContaRepository contaRepository;

	@Autowired
	SaldoBancarioRepository saldoBancarioRepository;

	@Autowired
	RegistroBancarioRepository registroBancarioRepository;

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public void instantiateTestDatabase() throws ParseException {

		Categoria categoria1 = new Categoria(null, "Operação Bancária",
				"Operações de Saque, Depósito, Transferências, etc.", null);
		Categoria categoria2 = new Categoria(null, "Compras no mercado", null, null);
		Categoria categoria3 = new Categoria(null, "Compras na farmácia", null, null);
		Categoria categoria4 = new Categoria(null, "Viagens", null, null);
		Categoria categoria5 = new Categoria(null, "Variados", null, null);

		Usuario usuario0 = new Usuario(null, "Admin", null, "admin@myhomefinances.com",
				bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Usuario usuario1 = new Usuario(null, "Ederson", "Lehugeur", "eder.lehugeur@gmail.com",
				bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Usuario usuario2 = new Usuario(null, "Fulano", "da Silva", "fulano@gmail.com",
				bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Perfil perfil1 = new Perfil(null, "ROLE_CLIENTE");
		Perfil perfil2 = new Perfil(null, "ROLE_ADMIN");

		usuario0.getPerfis().addAll(Arrays.asList(perfil1, perfil2));
		usuario1.getPerfis().addAll(Arrays.asList(perfil1));
		usuario2.getPerfis().addAll(Arrays.asList(perfil1));

		perfil1.getUsuarios().addAll(Arrays.asList(usuario0, usuario1, usuario2));
		perfil2.getUsuarios().addAll(Arrays.asList(usuario0));

		perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));
		usuarioRepository.saveAll(Arrays.asList(usuario0, usuario1, usuario2));

		categoria1.setUsuario(usuario1);
		categoria2.setUsuario(usuario1);
		categoria3.setUsuario(usuario1);
		categoria4.setUsuario(usuario1);
		categoria5.setUsuario(usuario1);

		Item item1 = new Item("Saque", null, categoria1, usuario1);
		Item item2 = new Item("Depósito", null, categoria1, usuario1);
		Item item3 = new Item("Transferência", null, categoria1, usuario1);
		Item item4 = new Item("Salário", null, categoria1, usuario1);
		Item item5 = new Item("Saldo inicial", null, categoria5, usuario1);

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3,
				categoria4, categoria5));
		itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4,
				item5));

		TipoRegistro tipoRegistro1 = new TipoRegistro(null, "Pagamento", 1);
		TipoRegistro tipoRegistro2 = new TipoRegistro(null, "Saque", 0);
		TipoRegistro tipoRegistro3 = new TipoRegistro(null, "Ganho", 0);
		TipoRegistro tipoRegistro4 = new TipoRegistro(null, "Salário", 0);
		TipoRegistro tipoRegistro5 = new TipoRegistro(null, "Saldo inicial", 0);

		tipoRegistroRepository.saveAll(Arrays.asList(tipoRegistro1, tipoRegistro2,
				tipoRegistro3, tipoRegistro4, tipoRegistro5));

		LocalDateTime hoje = LocalDateTime.now();

		Saldo saldo1 = new Saldo(null, 0.00, usuario1);

		saldoRepository.saveAll(Arrays.asList(saldo1));

		Banco banco1 = new Banco("NuBank");
		Banco banco2 = new Banco("Caixa Econômica Federal");

		bancoRepository.saveAll(Arrays.asList(banco1, banco2));

		TipoConta tipoConta1 = new TipoConta(null, "Conta Digital");
		TipoConta tipoConta2 = new TipoConta(null, "Conta Corrente");
		TipoConta tipoConta3 = new TipoConta(null, "Conta Poupança");

		tipoContaRepository.saveAll(Arrays.asList(tipoConta1, tipoConta2, tipoConta3));

		Conta conta1 = new Conta(null, banco1, tipoConta1, usuario1);

		contaRepository.saveAll(Arrays.asList(conta1));

		SaldoBancario saldoBancario1 = new SaldoBancario(null, 0.00, conta1);

		saldoBancarioRepository.saveAll(Arrays.asList(saldoBancario1));

		Registro registro1 = new Registro(null, 0.00, hoje, tipoRegistro5, usuario1, item5);

		registroRepository.saveAll(Arrays.asList(registro1));
	}
}
