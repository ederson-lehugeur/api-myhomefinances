package br.com.myhomefinances;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repositories.CategoriaRepository;
import br.com.myhomefinances.repositories.ItemRepository;
import br.com.myhomefinances.repositories.RegistroRepository;
import br.com.myhomefinances.repositories.SaldoRepository;
import br.com.myhomefinances.repositories.TipoRegistroRepository;
import br.com.myhomefinances.repositories.UsuarioRepository;

@SpringBootApplication
public class MyhomefinancesApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(MyhomefinancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Operação Bancária",
				"Operações de Saque, Depósito, Transferências, etc.");
		Categoria categoria2 = new Categoria(null, "Compras no mercado", null);
		Categoria categoria3 = new Categoria(null, "Compras na farmácia", null);
		Categoria categoria4 = new Categoria(null, "Viagens", null);

		Item item1 = new Item(null, "Saque", null, categoria1);
		Item item2 = new Item(null, "Depósito", null, categoria1);
		Item item3 = new Item(null, "Transferência", null, categoria1);

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4));
		itemRepository.saveAll(Arrays.asList(item1, item2, item3));

		TipoRegistro tipoRegistro1 = new TipoRegistro(null, "Pagamento");
		TipoRegistro tipoRegistro2 = new TipoRegistro(null, "Pendencia");
		TipoRegistro tipoRegistro3 = new TipoRegistro(null, "Saque");
		TipoRegistro tipoRegistro4 = new TipoRegistro(null, "Ganho");
		TipoRegistro tipoRegistro5 = new TipoRegistro(null, "Salário");

		tipoRegistroRepository.saveAll(Arrays.asList(tipoRegistro1, tipoRegistro2, tipoRegistro3, tipoRegistro4, tipoRegistro5));

		Usuario usuario1 = new Usuario(null, "Ederson", "Lehugeur", "eder.lehugeur@gmail.com", "123456");

		usuarioRepository.saveAll(Arrays.asList(usuario1));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

		Registro registro1 = new Registro(null, 100.00, sdf.parse("22/11/2019 16:00:00.000"), tipoRegistro3, usuario1,
				item1);

		registroRepository.saveAll(Arrays.asList(registro1));

		Saldo saldo1 = new Saldo(null, 1000.00, sdf.parse("21/11/2019 14:00:00.000"), usuario1);
		Saldo saldo2 = new Saldo(null, 900.00, sdf.parse("22/11/2019 16:00:01.000"), usuario1);

		saldoRepository.saveAll(Arrays.asList(saldo1, saldo2));

	}

}
