package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.ContaDTO;
import br.com.myhomefinances.repositories.ContaRepository;
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

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		List<Conta> listaContas = contaRepository.findAllByUsuario(usuario);

		return listaContas;
	}

	public Conta findByIdAndUsuario(Integer idConta) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		Optional<Conta> conta = contaRepository.findByIdAndUsuario(idConta, usuario);

		return conta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Conta.class.getName()));
	}

	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		return contaRepository.findAllByUsuario(usuario, pageRequest);
	}

	public Conta insert(Conta conta) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null || user.getId() != conta.getUsuario().getId()) {
			throw new AuthorizationException("Acesso negado");
		}

		conta.setId(null);

		conta = contaRepository.save(conta);

		return conta;
	}

	public Conta update(Conta conta) {
		Conta novaConta = findByIdAndUsuario(conta.getId());

		updateData(novaConta, conta);

		return contaRepository.save(novaConta);
	}

	public void delete(Integer id) {
		findByIdAndUsuario(id);

		contaRepository.deleteById(id);
	}

	public Conta fromDTO(ContaDTO contaDto) {
		Banco banco = bancoService.find(contaDto.getBancoId());

		TipoConta tipoConta = tipoContaService.find(contaDto.getTipoContaId());

		Usuario usuario = usuarioService.find(contaDto.getUsuarioId());

		return new Conta(contaDto.getId(), banco, tipoConta, usuario);
	}

	private void updateData(Conta novaConta, Conta conta) {
		novaConta.setBanco(conta.getBanco());
		novaConta.setTipoConta(conta.getTipoConta());
		novaConta.setUsuario(conta.getUsuario());
	}

}
