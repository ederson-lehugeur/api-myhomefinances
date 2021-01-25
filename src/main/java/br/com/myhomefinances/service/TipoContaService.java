package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.repository.TipoContaRepository;
import br.com.myhomefinances.resource.dto.TipoContaDto;
import br.com.myhomefinances.resource.form.TipoContaForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class TipoContaService {

	@Autowired
	TipoContaRepository tipoContaRepository;

	public List<TipoConta> findAll() {
		List<TipoConta> tiposContas = tipoContaRepository.findAll();

		return tiposContas;
	}

	public TipoConta findById(Long id) {
		Optional<TipoConta> tipoConta = tipoContaRepository.findById(id);

		return tipoConta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				TipoConta.class.getName()));
	}

	public TipoConta insert(TipoConta tipoConta) {
		tipoConta.setId(null);

		tipoConta = tipoContaRepository.save(tipoConta);

		return tipoConta;
	}

	public TipoConta update(TipoConta tipoConta) {
		TipoConta novoTipoConta = findById(tipoConta.getId());

		updateData(novoTipoConta, tipoConta);

		return tipoContaRepository.save(novoTipoConta);
	}

	public void delete(Long id) {
		findById(id);

		tipoContaRepository.deleteById(id);
	}

	public TipoConta convertToEntity(TipoContaForm tipoContaForm) {
		return new TipoConta(null, tipoContaForm.getNome());
	}

	public List<TipoContaDto> convertToDto(List<TipoConta> tiposContas) {
		return tiposContas.stream().map(TipoContaDto::new).collect(Collectors.toList());
	}

	private void updateData(TipoConta novoTipoConta, TipoConta tipoConta) {
		novoTipoConta.setNome(tipoConta.getNome());
	}

}
