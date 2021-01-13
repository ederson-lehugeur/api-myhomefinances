package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.dto.ContaDto;
import br.com.myhomefinances.form.ContaForm;
import br.com.myhomefinances.service.ContaService;
import br.com.myhomefinances.service.UsuarioService;

@RestController
@RequestMapping(value="contas")
public class ContaResource {

	@Autowired
	ContaService contaService;

	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<ContaDto>> findAllByUsuario() {
		List<Conta> contas = contaService.findAllByUsuario();

		return ResponseEntity.ok().body(contaService.convertToContaDto(contas));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ContaDto> findByIdAndUsuario(@PathVariable Long id) {
		Conta conta = contaService.findByIdAndUsuario(id);

		return ResponseEntity.ok().body(new ContaDto(conta));
	}

	@GetMapping(value="/pageable")
	public ResponseEntity<Page<ContaDto>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="id") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Conta> contasPage = contaService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(contaService.convertToContaDto(contasPage));
	}

	@PostMapping
	public ResponseEntity<ContaDto> insert(@Valid @RequestBody ContaForm contaForm,
			UriComponentsBuilder uriBuilder) {

		Conta conta = contaService.convertToConta(contaForm);

		conta = contaService.insert(conta);

		URI	uri = uriBuilder.path("/contas/{id}").buildAndExpand(conta.getId()).toUri();

		return ResponseEntity.created(uri).body(new ContaDto(conta));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<ContaDto> update(@PathVariable Long id,
			@Valid @RequestBody ContaForm contaForm) {

		Conta conta = contaService.convertToConta(contaForm);

		conta.setId(id);
		conta = contaService.update(conta);

		return ResponseEntity.ok(new ContaDto(conta));
	}

	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		contaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
