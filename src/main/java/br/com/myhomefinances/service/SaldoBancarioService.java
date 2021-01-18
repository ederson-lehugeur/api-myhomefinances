package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.dto.SaldoBancarioDto;
import br.com.myhomefinances.repository.SaldoBancarioRepository;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class SaldoBancarioService {

	@Autowired
	SaldoBancarioRepository saldoBancarioRepository;

	@Autowired
	ContaService contaService;

	public List<SaldoBancario> findByContaOrderByDataHoraDesc(Long idConta) {
		Conta conta = contaService.findById(idConta);

		List<SaldoBancario> saldosBancarios = saldoBancarioRepository.findByContaOrderByDataHoraCriacaoDesc(conta);

		return saldosBancarios;
	}

	public SaldoBancario findByIdAndConta(Long idSaldoBancario, Long idConta) {
		Conta conta = contaService.findById(idConta);

		Optional<SaldoBancario> saldoBancario = saldoBancarioRepository.findByIdAndConta(idSaldoBancario, conta);

		return saldoBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				SaldoBancario.class.getName()));
	}

	public SaldoBancario findFirstByContaOrderByDataHoraDesc(Conta conta) {
		return saldoBancarioRepository.findFirstByContaOrderByDataHoraCriacaoDesc(conta);
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
