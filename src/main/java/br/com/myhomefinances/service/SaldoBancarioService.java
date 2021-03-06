package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.repository.SaldoBancarioRepository;
import br.com.myhomefinances.resource.dto.SaldoBancarioDto;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class SaldoBancarioService {

	@Autowired
	SaldoBancarioRepository saldoBancarioRepository;

	@Autowired
	ContaService contaService;

	public List<SaldoBancario> findByContaOrderByDataHoraDesc(Long idConta) {
		Conta conta = contaService.findById(idConta);

		List<SaldoBancario> saldosBancarios = saldoBancarioRepository.findByContaIdOrderByDataHoraCriacaoDesc(conta.getId());

		return saldosBancarios;
	}

	public SaldoBancario findByIdAndConta(Long idSaldoBancario, Long idConta) {
		Conta conta = contaService.findById(idConta);

		Optional<SaldoBancario> saldoBancario = saldoBancarioRepository.findByIdAndContaId(idSaldoBancario, conta.getId());

		return saldoBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				SaldoBancario.class.getName()));
	}

	public SaldoBancario findFirstByContaOrderByDataHoraDesc(Conta conta) {
		return saldoBancarioRepository.findFirstByContaIdOrderByDataHoraCriacaoDesc(conta.getId());
	}

	public SaldoBancario insert(SaldoBancario saldoBancario) {
		saldoBancario.setId(null);

		saldoBancario = saldoBancarioRepository.save(saldoBancario);

		return saldoBancario;
	}

	public List<SaldoBancarioDto> convertToDto(List<SaldoBancario> saldosBancarios) {
		return saldosBancarios.stream().map(SaldoBancarioDto::new).collect(Collectors.toList());
	}

}
