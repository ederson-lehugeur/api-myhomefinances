package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.ContaRepository;
import br.com.myhomefinances.resource.dto.ContaDto;
import br.com.myhomefinances.resource.form.ContaForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

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

	public List<Conta> findAll() {
		Usuario usuario = UsuarioService.authenticated();

		List<Conta> contas = contaRepository.findAllByUsuarioId(usuario.getId());

		return contas;
	}

	public Conta findById(Long idConta) {
		Usuario usuario = UsuarioService.authenticated();

		Optional<Conta> conta = contaRepository.findByIdAndUsuarioId(idConta, usuario.getId());

		return conta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Conta.class.getName()));
	}

	public Conta insert(Conta conta) {
		Usuario usuario = UsuarioService.authenticated();

		conta.setId(null);
		conta.setUsuario(usuario);

		conta = contaRepository.save(conta);

		return conta;
	}

	public Conta update(Conta conta) {
		Conta novaConta = findById(conta.getId());

		updateData(novaConta, conta);

		return contaRepository.save(novaConta);
	}

	public void delete(Long id) {
		findById(id);

		contaRepository.deleteById(id);
	}

	public Conta convertToEntity(ContaForm contaForm) {
		Banco banco = bancoService.findById(contaForm.getBancoId());

		TipoConta tipoConta = tipoContaService.findById(contaForm.getTipoContaId());

		Usuario usuario = usuarioService.findById(contaForm.getUsuarioId());

		return new Conta(null, banco, tipoConta, usuario);
	}

	public List<ContaDto> convertToDto(List<Conta> contas) {
		return contas.stream().map(ContaDto::new).collect(Collectors.toList());
	}

	public Page<ContaDto> convertToDto(Page<Conta> contasPage) {
		return contasPage.map(ContaDto::new);
	}

	private void updateData(Conta novaConta, Conta conta) {
		novaConta.setBanco(conta.getBanco());
		novaConta.setTipoConta(conta.getTipoConta());
		novaConta.setUsuario(conta.getUsuario());
	}

}
