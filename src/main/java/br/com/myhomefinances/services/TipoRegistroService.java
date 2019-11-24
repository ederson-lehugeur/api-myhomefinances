package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.repositories.TipoRegistroRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class TipoRegistroService {

	@Autowired
	TipoRegistroRepository tipoRegistroRepository;

	public List<TipoRegistro> findAll() {
		List<TipoRegistro> listaTipoRegistro = tipoRegistroRepository.findAll();

		return listaTipoRegistro;
	}

	public TipoRegistro find(Integer id) {
		Optional<TipoRegistro> tipoRegistro = tipoRegistroRepository.findById(id);

		return tipoRegistro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, TipoRegistro.class.getName()));
	}

}
