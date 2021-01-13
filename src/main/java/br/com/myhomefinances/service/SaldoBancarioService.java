package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.repository.SaldoBancarioRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class SaldoBancarioService {

	@Autowired
	SaldoBancarioRepository saldoBancarioRepository;

	@Autowired
	ContaService contaService;

	public List<SaldoBancario> findByContaOrderByDataHoraDesc(Long idConta) {
		Conta conta = contaService.findByIdAndUsuario(idConta);

		List<SaldoBancario> listaSaldoBancarios = saldoBancarioRepository.findByContaOrderByDataHoraDesc(conta);

		return listaSaldoBancarios;
	}

	public SaldoBancario findByIdAndConta(Long idSaldoBancario, Long idConta) {
		Conta conta = contaService.findByIdAndUsuario(idConta);

		Optional<SaldoBancario> saldoBancario = saldoBancarioRepository.findByIdAndConta(idSaldoBancario, conta);

		return saldoBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				SaldoBancario.class.getName()));
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
