package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.dto.TipoRegistroDTO;
import br.com.myhomefinances.repositories.TipoRegistroRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class TipoRegistroService {

	@Autowired
	TipoRegistroRepository tipoRegistroRepository;

	public List<TipoRegistro> find() {
		List<TipoRegistro> listaTipoRegistro = tipoRegistroRepository.findAll();

		return listaTipoRegistro;
	}

	public TipoRegistro findById(Integer id) {
		Optional<TipoRegistro> tipoRegistro = tipoRegistroRepository.findById(id);

		return tipoRegistro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				TipoRegistro.class.getName()));
	}

	public TipoRegistro findByNome(String nome) {
		Optional<TipoRegistro> tipoRegistro = tipoRegistroRepository.findByNome(nome);

		return tipoRegistro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				TipoRegistro.class.getName()));
	}

	public TipoRegistro insert(TipoRegistro tipoRegistro) {
		tipoRegistro.setId(null);

		tipoRegistro = tipoRegistroRepository.save(tipoRegistro);

		return tipoRegistro;
	}

	public TipoRegistro update(TipoRegistro tipoRegistro) {
		TipoRegistro novoTipoRegistro = findById(tipoRegistro.getId());

		updateData(novoTipoRegistro, tipoRegistro);

		return tipoRegistroRepository.save(novoTipoRegistro);
	}

	public void delete(Integer id) {
		findById(id);

		tipoRegistroRepository.deleteById(id);
	}

	public TipoRegistro fromDTO(TipoRegistroDTO tipoRegistroDto) {
		return new TipoRegistro(tipoRegistroDto.getId(), tipoRegistroDto.getNome(),
				tipoRegistroDto.getEhRegistroDeSaida());
	}

	private void updateData(TipoRegistro novoTipoRegistro, TipoRegistro tipoRegistro) {
		novoTipoRegistro.setNome(tipoRegistro.getNome());
	}

}
