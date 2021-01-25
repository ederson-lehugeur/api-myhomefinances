package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.repository.TipoRegistroRepository;
import br.com.myhomefinances.resource.dto.TipoRegistroDto;
import br.com.myhomefinances.resource.form.TipoRegistroForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class TipoRegistroService {

	@Autowired
	TipoRegistroRepository tipoRegistroRepository;

	public List<TipoRegistro> findAll() {
		List<TipoRegistro> tiposRegistros = tipoRegistroRepository.findAll();

		return tiposRegistros;
	}

	public TipoRegistro findById(Long id) {
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

	public void delete(Long id) {
		findById(id);

		tipoRegistroRepository.deleteById(id);
	}

	public TipoRegistro convertToEntity(TipoRegistroForm tipoRegistroForm) {
		return new TipoRegistro(null, tipoRegistroForm.getNome(),
				tipoRegistroForm.getEhRegistroDeSaida());
	}

	public List<TipoRegistroDto> convertToDto(List<TipoRegistro> tiposRegistros) {
		return tiposRegistros.stream().map(TipoRegistroDto::new).collect(Collectors.toList());
	}

	private void updateData(TipoRegistro novoTipoRegistro, TipoRegistro tipoRegistro) {
		novoTipoRegistro.setNome(tipoRegistro.getNome());
	}

}
