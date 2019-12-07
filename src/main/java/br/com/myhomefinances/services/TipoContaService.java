package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.repositories.TipoContaRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class TipoContaService {

	@Autowired
	TipoContaRepository tipoContaRepository;

	public List<TipoConta> findAll() {
		List<TipoConta> listaTiposContas = tipoContaRepository.findAll();

		return listaTiposContas;
	}

	public TipoConta find(Integer id) {
		Optional<TipoConta> tipoConta = tipoContaRepository.findById(id);

		return tipoConta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, TipoConta.class.getName()));
	}

	public TipoConta insert(TipoConta tipoConta) {
		tipoConta.setId(null);

		tipoConta = tipoContaRepository.save(tipoConta);

		return tipoConta;
	}

	public TipoConta update(TipoConta tipoConta) {
		TipoConta novoTipoConta = find(tipoConta.getId());

		updateData(novoTipoConta, tipoConta);

		return tipoContaRepository.save(novoTipoConta);
	}

	public void delete(Integer id) {
		find(id);

		tipoContaRepository.deleteById(id);
	}

	private void updateData(TipoConta novoTipoConta, TipoConta tipoConta) {
		novoTipoConta.setNome(tipoConta.getNome());
	}

}
