package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.RegistroDto;
import br.com.myhomefinances.form.RegistroForm;
import br.com.myhomefinances.repository.RegistroRepository;
import br.com.myhomefinances.service.exception.NegativeBalanceException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class RegistroService {

	@Autowired
	RegistroRepository registroRepository;

	@Autowired
	SaldoService saldoService;

	@Autowired
	TipoRegistroService tipoRegistroService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ItemService itemService;

	public Page<Registro> findAll(Pageable paginacao) {
		Usuario usuario = UsuarioService.authenticated();

		Page<Registro> registrosPage = registroRepository.findByUsuarioId(usuario.getId(), paginacao);

		return registrosPage;
	}

	public Registro findById(Long id) {
		Usuario usuario = UsuarioService.authenticated();

		Optional<Registro> registro = registroRepository.findByIdAndUsuarioId(id, usuario.getId());

		return registro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Registro.class.getName()));
	}

	// Refatorar
	@Transactional
	public Registro insert(Registro registro) {
		Usuario usuario = UsuarioService.authenticated();

		registro.setId(null);

		registro = registroRepository.save(registro);

		Saldo saldo = saldoService.findFirstOrderByDataHoraDesc();

		Double valor;

		if (registro.getTipoRegistro().getEhRegistroDeSaida() == 1) {
			valor = saldo.getSaldo() - registro.getValor();
		} else {
			valor = saldo.getSaldo() + registro.getValor();
		}

		if (valor < 0) {
			throw new NegativeBalanceException("Saldo com valor negativo");
		}

		Saldo novoSaldo = new Saldo(null, valor, registro.getUsuario());

		saldoService.insert(novoSaldo);

		return registro;
	}

	@Transactional
	public void delete(Long id) {
		Registro registro = findById(id);

		Saldo saldo = saldoService.findFirstOrderByDataHoraDesc();

		Double valor;

		if (registro.getTipoRegistro().getEhRegistroDeSaida() == 1) {
			valor = saldo.getSaldo() + registro.getValor();
		} else {
			valor = saldo.getSaldo() - registro.getValor();
		}

		if (valor < 0) {
			throw new NegativeBalanceException("Saldo com valor negativo");
		}

		Saldo novoSaldo = new Saldo(null, valor, registro.getUsuario());

		saldoService.insert(novoSaldo);

		registroRepository.deleteById(id);
	}

	public Registro convertToEntity(RegistroForm registroForm) {
		Usuario usuario = UsuarioService.authenticated();

		TipoRegistro tipoRegistro = tipoRegistroService.findById(registroForm.getTipoRegistroId());

		Item item = itemService.findById(registroForm.getItemId());

		return new Registro(null, registroForm.getValor(), registroForm.getDataHora(),
				tipoRegistro, usuario, item);
	}

	public List<RegistroDto> convertToDto(List<Registro> registros) {
		return registros.stream().map(RegistroDto::new).collect(Collectors.toList());
	}

	public Page<RegistroDto> convertToDto(Page<Registro> registrosPage) {
		return registrosPage.map(RegistroDto::new);
	}

}
