package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.SaldoRepository;
import br.com.myhomefinances.resource.dto.SaldoDto;
import br.com.myhomefinances.service.exception.NegativeBalanceException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class SaldoService {

	@Autowired
	SaldoRepository saldoRepository;

	@Autowired
	UsuarioService usuarioService;

	public Saldo find() {
		Usuario usuario = UsuarioService.authenticated();

		Optional<Saldo> saldo = saldoRepository.findByUsuarioId(usuario.getId());

		return saldo.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Saldo.class.getName()));
	}

	public Saldo insert(Saldo saldo) {
		saldo.setId(null);

		saldo = saldoRepository.save(saldo);

		return saldo;
	}

	public List<SaldoDto> convertToDto(List<Saldo> saldos) {
		return saldos.stream().map(SaldoDto::new).collect(Collectors.toList());
	}

	public void updateSaldoDeRegistroAdicionado(Registro registro) {
		Saldo saldo = find();

		Double valor;

		if (registro.getTipoRegistro().getEhRegistroDeSaida() == 1) {
			valor = saldo.getSaldo() - registro.getValor();
		} else {
			valor = saldo.getSaldo() + registro.getValor();
		}

		if (valor == null || valor < 0) {
			throw new NegativeBalanceException("Saldo inválido");
		}

		saldo.setSaldo(valor);

		saldo = saldoRepository.save(saldo);
	}

	public void updateSaldoDeRegistroDeletado(Registro registro) {
		Saldo saldo = find();

		Double valor;

		if (registro.getTipoRegistro().getEhRegistroDeSaida() == 1) {
			valor = saldo.getSaldo() + registro.getValor();
		} else {
			valor = saldo.getSaldo() - registro.getValor();
		}

		if (valor == null || valor < 0) {
			throw new NegativeBalanceException("Saldo inválido");
		}

		saldo.setSaldo(valor);

		saldo = saldoRepository.save(saldo);
	}

	public void updateSaldoDeRegistroAtualizado(Registro registro) {
		Saldo saldo = find();

		Double valor;
		Double diferenca = registro.getValor() - registro.getValorPreAtualizacao();

		if (registro.getTipoRegistro().getEhRegistroDeSaida() == 1) {
			valor = saldo.getSaldo() - diferenca;
		} else {
			valor = saldo.getSaldo() + diferenca;
		}

		if (valor == null || valor < 0) {
			throw new NegativeBalanceException("Saldo inválido");
		}

		saldo.setSaldo(valor);

		saldo = saldoRepository.save(saldo);
	}

}
