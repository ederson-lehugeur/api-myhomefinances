package br.com.myhomefinances.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repositories.BancoRepository;
import br.com.myhomefinances.repositories.CategoriaRepository;
import br.com.myhomefinances.repositories.ContaRepository;
import br.com.myhomefinances.repositories.ItemRepository;
import br.com.myhomefinances.repositories.PerfilRepository;
import br.com.myhomefinances.repositories.RegistroBancarioRepository;
import br.com.myhomefinances.repositories.RegistroRepository;
import br.com.myhomefinances.repositories.SaldoBancarioRepository;
import br.com.myhomefinances.repositories.SaldoRepository;
import br.com.myhomefinances.repositories.TipoContaRepository;
import br.com.myhomefinances.repositories.TipoRegistroRepository;
import br.com.myhomefinances.repositories.UsuarioRepository;

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

		Usuario usuario1 = new Usuario(null, "Ederson", "Lehugeur", "eder.lehugeur@gmail.com",
				bCryptPasswordEncoder.encode("Q1W2E3"));

		Perfil perfil1 = new Perfil(null, "ROLE_CLIENTE");
		Perfil perfil2 = new Perfil(null, "ROLE_ADMIN");

		usuario1.getPerfis().addAll(Arrays.asList(perfil1, perfil2));

		perfil1.getUsuarios().addAll(Arrays.asList(usuario1));
		perfil2.getUsuarios().addAll(Arrays.asList(usuario1));

		perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));
		usuarioRepository.saveAll(Arrays.asList(usuario1));

		categoria1.setUsuario(usuario1);
		categoria2.setUsuario(usuario1);
		categoria3.setUsuario(usuario1);
		categoria4.setUsuario(usuario1);
		categoria5.setUsuario(usuario1);

		Item item1 = new Item(null, "Saque", null, categoria1, usuario1);
		Item item2 = new Item(null, "Depósito", null, categoria1, usuario1);
		Item item3 = new Item(null, "Transferência", null, categoria1, usuario1);
		Item item4 = new Item(null, "Salário", null, categoria1, usuario1);

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3,
				categoria4, categoria5));
		itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4));

		TipoRegistro tipoRegistro1 = new TipoRegistro(null, "Pagamento");
		TipoRegistro tipoRegistro2 = new TipoRegistro(null, "Pendencia");
		TipoRegistro tipoRegistro3 = new TipoRegistro(null, "Saque");
		TipoRegistro tipoRegistro4 = new TipoRegistro(null, "Ganho");
		TipoRegistro tipoRegistro5 = new TipoRegistro(null, "Salário");

		tipoRegistroRepository.saveAll(Arrays.asList(tipoRegistro1, tipoRegistro2,
				tipoRegistro3, tipoRegistro4, tipoRegistro5));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

		String now = sdf.format(new Date());

		Saldo saldo1 = new Saldo(null, 0.00, sdf.parse(now), usuario1);

		saldoRepository.saveAll(Arrays.asList(saldo1));

		Banco banco1 = new Banco(null, "NuBank");
		Banco banco2 = new Banco(null, "Caixa Econômica Federal");

		bancoRepository.saveAll(Arrays.asList(banco1, banco2));

		TipoConta tipoConta1 = new TipoConta(null, "Conta Digital");
		TipoConta tipoConta2 = new TipoConta(null, "Conta Corrente");
		TipoConta tipoConta3 = new TipoConta(null, "Conta Poupança");

		tipoContaRepository.saveAll(Arrays.asList(tipoConta1, tipoConta2, tipoConta3));

		Conta conta1 = new Conta(null, banco1, tipoConta1, usuario1);

		contaRepository.saveAll(Arrays.asList(conta1));

		SaldoBancario saldoBancario1 = new SaldoBancario(null, 0.00, sdf.parse(now), conta1);

		saldoBancarioRepository.saveAll(Arrays.asList(saldoBancario1));
	}
}
