package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.ContaDto;
import br.com.myhomefinances.form.ContaForm;
import br.com.myhomefinances.repository.ContaRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class ContaService {

	@Autowired
	ContaRepository contaRepository;

	@Autowired
	BancoService bancoService;

	@Autowired
	TipoContaService tipoContaService;

	@Autowired
	UsuarioService usuarioService;

	public List<Conta> findAllByUsuario() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		Usuario usuario = usuarioService.find(user.getId());

		List<Conta> contas = contaRepository.findAllByUsuario(usuario);

		return contas;
	}

	public Conta findByIdAndUsuario(Long idConta) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		Usuario usuario = usuarioService.find(user.getId());

		Optional<Conta> conta = contaRepository.findByIdAndUsuario(idConta, usuario);

		return conta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Conta.class.getName()));
	}

	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		Usuario usuario = usuarioService.find(user.getId());

		return contaRepository.findAllByUsuario(usuario, pageRequest);
	}

	public Conta insert(Conta conta) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		conta.setId(null);

		conta = contaRepository.save(conta);

		return conta;
	}

	public Conta update(Conta conta) {
		Conta novaConta = findByIdAndUsuario(conta.getId());

		updateData(novaConta, conta);

		return contaRepository.save(novaConta);
	}

	public void delete(Long id) {
		findByIdAndUsuario(id);

		contaRepository.deleteById(id);
	}

	public Conta convertToConta(ContaForm contaForm) {
		Banco banco = bancoService.findById(contaForm.getBancoId());

		TipoConta tipoConta = tipoContaService.find(contaForm.getTipoContaId());

		Usuario usuario = usuarioService.find(contaForm.getUsuarioId());

		return new Conta(null, banco, tipoConta, usuario);
	}

	public List<ContaDto> convertToContaDto(List<Conta> contas) {
		return contas.stream().map(ContaDto::new).collect(Collectors.toList());
	}

	public Page<ContaDto> convertToContaDto(Page<Conta> contasPage) {
		return contasPage.map(ContaDto::new);
	}

	private void updateData(Conta novaConta, Conta conta) {
		novaConta.setBanco(conta.getBanco());
		novaConta.setTipoConta(conta.getTipoConta());
		novaConta.setUsuario(conta.getUsuario());
	}

	// Criar anotação para injetar usuário logado.
	private UserDetailsSpringSecurity getUserAuthenticated() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return user;
	}

}
