package br.com.myhomefinances;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.repositories.CategoriaRepository;
import br.com.myhomefinances.repositories.ItemRepository;

@SpringBootApplication
public class MyhomefinancesApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyhomefinancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Operação Bancária",
				"Operações de Saque, Depósito, Transferências, etc.");

		Item item1 = new Item(null, "Saque", null, categoria1);
		Item item2 = new Item(null, "Depósito", null, categoria1);
		Item item3 = new Item(null, "Transferência", null, categoria1);

		categoria1.getItens().addAll(Arrays.asList(item1, item2, item3));

		categoriaRepository.saveAll(Arrays.asList(categoria1));
		itemRepository.saveAll(Arrays.asList(item1, item2, item3));

	}

}
