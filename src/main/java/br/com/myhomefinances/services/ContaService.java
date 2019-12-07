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

	public List<Conta> findAll() {
		List<Conta> listaContas = contaRepository.findAll();

		return listaContas;
	}

	public Conta find(Integer id) {
		Optional<Conta> conta = contaRepository.findById(id);

		return conta.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Conta.class.getName()));
	}

	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return contaRepository.findAll(pageRequest);
	}

	public Conta insert(Conta conta) {
		conta.setId(null);

		conta = contaRepository.save(conta);

		return conta;
	}

	public Conta update(Conta conta) {
		Conta novaConta = find(conta.getId());

		updateData(novaConta, conta);

		return contaRepository.save(novaConta);
	}

	public void delete(Integer id) {
		find(id);

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
