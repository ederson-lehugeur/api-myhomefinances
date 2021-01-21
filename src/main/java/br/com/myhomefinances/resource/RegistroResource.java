package br.com.myhomefinances.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.dto.RegistroDto;
import br.com.myhomefinances.form.RegistroForm;
import br.com.myhomefinances.service.RegistroService;

@RestController
@RequestMapping(value="registros")
public class RegistroResource {

	@Autowired
	RegistroService registroService;

	@GetMapping
	public ResponseEntity<Page<RegistroDto>> findAll(
			@PageableDefault(page=0, size=12, sort="dataHora", direction=Direction.DESC) Pageable paginacao) {

		Page<Registro> registrosPage = registroService.findAll(paginacao);

		return ResponseEntity.ok().body(registroService.convertToDto(registrosPage));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<RegistroDto> findById(@PathVariable Long id) {
		Registro registro = registroService.findById(id);

		return ResponseEntity.ok().body(new RegistroDto(registro));
	}

	@PostMapping
	public ResponseEntity<RegistroDto> insert(@Valid @RequestBody RegistroForm registroForm,
			UriComponentsBuilder uriBuilder) {

		Registro registro = registroService.convertToEntity(registroForm);

		registro = registroService.insert(registro);

		URI	uri = uriBuilder.path("/registros/{id}").buildAndExpand(registro.getId()).toUri();

		return ResponseEntity.created(uri).body(new RegistroDto(registro));
	}

	@PutMapping(value="/{id}")
	public ResponseEntity<RegistroDto> update(@PathVariable Long id, @Valid @RequestBody RegistroForm registroForm,
			UriComponentsBuilder uriBuilder) {

		Registro registro = registroService.convertToEntity(registroForm);

		registro.setId(id);
		registro = registroService.update(registro);

		return ResponseEntity.ok(new RegistroDto(registro));
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		registroService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
