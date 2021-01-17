package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.dto.TipoContaDto;
import br.com.myhomefinances.form.TipoContaForm;
import br.com.myhomefinances.service.TipoContaService;

@RestController
@RequestMapping(value="tipos-contas")
public class TipoContaResource {

	@Autowired
	TipoContaService tipoContaService;

	@GetMapping
	public ResponseEntity<List<TipoContaDto>> findAll() {
		List<TipoConta> tiposContas = tipoContaService.findAll();

		return ResponseEntity.ok().body(tipoContaService.convertToDto(tiposContas));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<TipoContaDto> findById(@PathVariable Long id) {
		TipoConta tipoConta = tipoContaService.findById(id);

		return ResponseEntity.ok().body(new TipoContaDto(tipoConta));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	@Transactional
	public ResponseEntity<TipoContaDto> insert(@Valid @RequestBody TipoContaForm tipoContaForm,
			UriComponentsBuilder uriBuilder) {

		TipoConta tipoConta = tipoContaService.convertToEntity(tipoContaForm);

		tipoConta = tipoContaService.insert(tipoConta);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/tipos-contas/{id}").buildAndExpand(tipoConta.getId()).toUri();

		return ResponseEntity.created(uri).body(new TipoContaDto(tipoConta));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<TipoContaDto> update(@PathVariable Long id,
			@Valid @RequestBody TipoContaForm tipoContaForm) {

		TipoConta tipoConta = tipoContaService.convertToEntity(tipoContaForm);

		tipoConta.setId(id);
		tipoConta = tipoContaService.update(tipoConta);

		return ResponseEntity.ok(new TipoContaDto(tipoConta));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		tipoContaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
