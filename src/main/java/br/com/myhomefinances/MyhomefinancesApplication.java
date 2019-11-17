package br.com.myhomefinances;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.repositories.CategoriaRepository;

@SpringBootApplication
public class MyhomefinancesApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyhomefinancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Operação Bancária",
				"Operações de Saque, Depósito, Transferências, etc.");

		categoriaRepository.saveAll(Arrays.asList(categoria1));

	}

}
