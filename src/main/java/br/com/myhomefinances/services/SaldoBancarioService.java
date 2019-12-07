package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.repositories.SaldoBancarioRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class SaldoBancarioService {

	@Autowired
	SaldoBancarioRepository saldoBancarioRepository;

	public List<SaldoBancario> findAll() {
		List<SaldoBancario> listaSaldoBancarios = saldoBancarioRepository.findAll();

		return listaSaldoBancarios;
	}

	public SaldoBancario find(Integer id) {
		Optional<SaldoBancario> saldoBancario = saldoBancarioRepository.findById(id);

		return saldoBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, SaldoBancario.class.getName()));
	}

	public SaldoBancario findFirstByContaOrderByDataHoraDesc(Conta conta) {
		return saldoBancarioRepository.findFirstByContaOrderByDataHoraDesc(conta);
	}

	public SaldoBancario insert(SaldoBancario saldoBancario) {
		saldoBancario.setId(null);

		saldoBancario = saldoBancarioRepository.save(saldoBancario);

		return saldoBancario;
	}

}
