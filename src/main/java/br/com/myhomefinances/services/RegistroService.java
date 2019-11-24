package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.repositories.RegistroRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class RegistroService {

	@Autowired
	RegistroRepository registroRepository;

	public List<Registro> findAll() {
		List<Registro> listaRegistros = registroRepository.findAll();

		return listaRegistros;
	}

	public Registro find(Integer id) {
		Optional<Registro> registro = registroRepository.findById(id);

		return registro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Registro.class.getName()));
	}

}
