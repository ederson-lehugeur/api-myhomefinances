package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.resource.dto.ContaDto;
import br.com.myhomefinances.resource.form.ContaForm;
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
		List<Conta> contas = contaService.findAll();

		return ResponseEntity.ok().body(contaService.convertToDto(contas));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ContaDto> findByIdAndUsuario(@PathVariable Long id) {
		Conta conta = contaService.findById(id);

		return ResponseEntity.ok().body(new ContaDto(conta));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ContaDto> insert(@Valid @RequestBody ContaForm contaForm,
			UriComponentsBuilder uriBuilder) {

		Conta conta = contaService.convertToEntity(contaForm);

		conta = contaService.insert(conta);

		URI	uri = uriBuilder.path("/contas/{id}").buildAndExpand(conta.getId()).toUri();

		return ResponseEntity.created(uri).body(new ContaDto(conta));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<ContaDto> update(@PathVariable Long id,
			@Valid @RequestBody ContaForm contaForm) {

		Conta conta = contaService.convertToEntity(contaForm);

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
