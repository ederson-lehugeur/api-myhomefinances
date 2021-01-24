package br.com.myhomefinances.service;

import java.text.ParseException;
import java.util.Arrays;

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
				"Operações de Saque, Depósito, Transferências, etc.");
		Categoria categoria2 = new Categoria(null, "Casa", null);
		Categoria categoria3 = new Categoria(null, "Educação", null);
		Categoria categoria4 = new Categoria(null, "Eletrônicos", null);
		Categoria categoria5 = new Categoria(null, "Lazer", null);
		Categoria categoria6 = new Categoria(null, "Outros", null);
		Categoria categoria7 = new Categoria(null, "Restaurante", null);
		Categoria categoria8 = new Categoria(null, "Saúde", null);
		Categoria categoria9 = new Categoria(null, "Serviços", null);
		Categoria categoria10 = new Categoria(null, "Supermercado", null);
		Categoria categoria11 = new Categoria(null, "Transporte", null);
		Categoria categoria12 = new Categoria(null, "Vestuário", null);
		Categoria categoria13 = new Categoria(null, "Viagem", null);

		Usuario usuario0 = new Usuario(null, "Admin", "Admin", "admin@myhomefinances.com",
				bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Usuario usuario1 = new Usuario(null, "Ederson", "Lehugeur", "eder.lehugeur@gmail.com",
				bCryptPasswordEncoder.encode("Q1W2E3R4"));

		Perfil perfil1 = new Perfil(null, "ROLE_CLIENTE");
		Perfil perfil2 = new Perfil(null, "ROLE_ADMIN");

		usuario0.getPerfis().addAll(Arrays.asList(perfil1, perfil2));
		usuario1.getPerfis().addAll(Arrays.asList(perfil1));

		perfil1.getUsuarios().addAll(Arrays.asList(usuario0, usuario1));
		perfil2.getUsuarios().addAll(Arrays.asList(usuario0));

		perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));
		usuarioRepository.saveAll(Arrays.asList(usuario0, usuario1));

		Item item1 = new Item("Saldo inicial", null, categoria1, usuario1);
		Item item2 = new Item("Saque", null, categoria1, usuario1);
		Item item3 = new Item("Depósito", null, categoria1, usuario1);
		Item item4 = new Item("Transferência", null, categoria1, usuario1);
		Item item5 = new Item("Salário", null, categoria1, usuario1);

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3,
				categoria4, categoria5, categoria6, categoria7, categoria8, categoria9,
				categoria10, categoria11, categoria12, categoria13));
		itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4,
				item5));

		TipoRegistro tipoRegistro1 = new TipoRegistro(null, "Entrada", 0);
		TipoRegistro tipoRegistro2 = new TipoRegistro(null, "Saida", 1);

		tipoRegistroRepository.saveAll(Arrays.asList(tipoRegistro1, tipoRegistro2));

		Saldo saldo1 = new Saldo(null, 0.00, usuario1);

		saldoRepository.saveAll(Arrays.asList(saldo1));

		Banco banco1 = new Banco(001, "Banco do Brasil S.A.");
		Banco banco2 = new Banco(033, "Banco Santander (Brasil) S.A.");
		Banco banco3 = new Banco(104, "Caixa Econômica Federal");
		Banco banco4 = new Banco(237, "Banco Bradesco S.A.");
		Banco banco5 = new Banco(341, "Banco Itaú S.A.");
		Banco banco6 = new Banco(356, "Banco Real S.A. (antigo)");
		Banco banco7 = new Banco(389, "Banco Mercantil do Brasil S.A.");
		Banco banco8 = new Banco(399, "HSBC Bank Brasil S.A. – Banco Múltiplo");
		Banco banco9 = new Banco(422, "Banco Safra S.A.");
		Banco banco10 = new Banco(453, "Banco Rural S.A.");
		Banco banco11 = new Banco(633, "Banco Rendimento S.A.");
		Banco banco12 = new Banco(652, "Itaú Unibanco Holding S.A.");
		Banco banco13 = new Banco(745, "Banco Citibank S.A.");
		Banco banco14 = new Banco(260, "Nu Pagamentos S.A.");
		Banco banco15 = new Banco(336, "Banco C6 S.A");
		Banco banco16 = new Banco(077, "Banco Inter S.A.");

		bancoRepository.saveAll(Arrays.asList(banco1, banco2, banco3, banco4, banco5,
				banco6, banco7, banco8, banco9, banco10, banco11, banco12, banco13,
				banco14, banco15, banco16));

		TipoConta tipoConta1 = new TipoConta(null, "Conta-corrente");
		TipoConta tipoConta2 = new TipoConta(null, "Conta poupança");
		TipoConta tipoConta3 = new TipoConta(null, "Conta-salário");
		TipoConta tipoConta4 = new TipoConta(null, "Conta universitária");
		TipoConta tipoConta5 = new TipoConta(null, "Conta digital");

		tipoContaRepository.saveAll(Arrays.asList(tipoConta1, tipoConta2, tipoConta3, tipoConta4, tipoConta5));

		Conta conta1 = new Conta(null, banco14, tipoConta5, usuario1);

		contaRepository.saveAll(Arrays.asList(conta1));

		SaldoBancario saldoBancario1 = new SaldoBancario(null, 0.00, conta1);

		saldoBancarioRepository.saveAll(Arrays.asList(saldoBancario1));
	}

}
