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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.dto.TipoRegistroDto;
import br.com.myhomefinances.form.TipoRegistroForm;
import br.com.myhomefinances.service.TipoRegistroService;

@RestController
@RequestMapping(value="tipos-registros")
public class TipoRegistroResource {

	@Autowired
	TipoRegistroService tipoRegistroService;

	@GetMapping
	public ResponseEntity<List<TipoRegistroDto>> findAll() {
		List<TipoRegistro> tiposRegistros = tipoRegistroService.findAll();

		return ResponseEntity.ok().body(tipoRegistroService.convertToDto(tiposRegistros));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<TipoRegistroDto> findById(@PathVariable Long id) {
		TipoRegistro tipoRegistro = tipoRegistroService.findById(id);

		return ResponseEntity.ok().body(new TipoRegistroDto(tipoRegistro));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	@Transactional
	public ResponseEntity<TipoRegistroDto> insert(@Valid @RequestBody TipoRegistroForm tipoRegistroForm,
			UriComponentsBuilder uriBuilder) {

		TipoRegistro tipoRegistro = tipoRegistroService.convertToEntity(tipoRegistroForm);

		tipoRegistro = tipoRegistroService.insert(tipoRegistro);

		URI	uri = uriBuilder.path("/tipos-registros/{id}").buildAndExpand(tipoRegistro.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<TipoRegistroDto> update(@PathVariable Long id,
			@Valid @RequestBody TipoRegistroForm tipoRegistroForm) {

		TipoRegistro tipoRegistro = tipoRegistroService.convertToEntity(tipoRegistroForm);

		tipoRegistro.setId(id);
		tipoRegistro = tipoRegistroService.update(tipoRegistro);

		return ResponseEntity.ok(new TipoRegistroDto(tipoRegistro));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		tipoRegistroService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
