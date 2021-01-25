package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.RegistroBancario;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.RegistroBancarioRepository;
import br.com.myhomefinances.resource.dto.RegistroBancarioDto;
import br.com.myhomefinances.resource.form.RegistroBancarioForm;
import br.com.myhomefinances.service.exception.AuthorizationException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class RegistroBancarioService {

	@Autowired
	RegistroBancarioRepository registroBancarioRepository;

	@Autowired
	RegistroService registroService;

	@Autowired
	TipoRegistroService tipoRegistroService;

	@Autowired
	SaldoBancarioService saldoBancarioService;

	@Autowired
	ContaService contaService;

	@Autowired
	ItemService itemService;

	@Autowired
	UsuarioService usuarioService;

	public Page<RegistroBancario> findAll(Pageable paginacao) {
		Page<RegistroBancario> registrosBancarios = registroBancarioRepository.findAll(paginacao);

		return registrosBancarios;
	}

	public RegistroBancario findById(Long idRegistroBancario) {
		Optional<RegistroBancario> registroBancario = registroBancarioRepository.findById(idRegistroBancario);

		return registroBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!", Registro.class.getName()));
	}

	public Page<RegistroBancario> findByConta(Long idConta, Pageable paginacao) {
		Conta conta = contaService.findById(idConta);

		Page<RegistroBancario> registrosBancarios = registroBancarioRepository.findByContaId(conta.getId(), paginacao);

		return registrosBancarios;
	}

	public RegistroBancario findByIdAndConta(Long idRegistroBancario, Long idConta) {
		Conta conta = contaService.findById(idConta);

		Optional<RegistroBancario> registroBancario = registroBancarioRepository.findByIdAndContaId(idRegistroBancario, conta.getId());

		return registroBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Registro.class.getName()));
	}

	// Refatorar
	@Transactional
	public RegistroBancario insert(RegistroBancario registroBancario) {
		Usuario usuario = UsuarioService.authenticated();

		if (usuario.getId() != registroBancario.getConta().getUsuario().getId()) {
			throw new AuthorizationException("Acesso negado");
		}

		registroBancario.setId(null);

		registroBancario = registroBancarioRepository.save(registroBancario);

		SaldoBancario saldoBancario = saldoBancarioService.findFirstByContaOrderByDataHoraDesc(registroBancario.getConta());

		Double valor = saldoBancario.getSaldo() + registroBancario.getValor();

		SaldoBancario novoSaldoBancario = new SaldoBancario(null, valor, registroBancario.getConta());

		saldoBancarioService.insert(novoSaldoBancario);

		// Adição automática de registro se a operação for uma saque.
		if (registroBancario.getItem().getNome().equals("Saque")) {
			TipoRegistro tipoRegistro = tipoRegistroService.findByNome("Saque");

			registroService.insert(new Registro(null, registroBancario.getValor(),
					registroBancario.getDataHora(), tipoRegistro,
					registroBancario.getConta().getUsuario(), registroBancario.getItem()));
		}

		return registroBancario;
	}

	public RegistroBancario convertToEntity(RegistroBancarioForm registroBancarioForm) {
		Conta conta = contaService.findById(registroBancarioForm.getContaId());

		Item item = itemService.findById(registroBancarioForm.getItemId());

		return new RegistroBancario(null, registroBancarioForm.getValor(),
				registroBancarioForm.getDataHora(), conta, item);
	}

	public List<RegistroBancarioDto> convertToDto(List<RegistroBancario> registrosBancarios) {
		return registrosBancarios.stream().map(RegistroBancarioDto::new).collect(Collectors.toList());
	}

	public Page<RegistroBancarioDto> convertToDto(Page<RegistroBancario> registrosBancariosPage) {
		return registrosBancariosPage.map(RegistroBancarioDto::new);
	}

}
