package br.com.myhomefinances.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.RegistroBancario;
import br.com.myhomefinances.resource.dto.RegistroBancarioDto;
import br.com.myhomefinances.resource.form.RegistroBancarioForm;
import br.com.myhomefinances.service.RegistroBancarioService;

@RestController
@RequestMapping(value="registros-bancarios")
public class RegistroBancarioResource {

	@Autowired
	RegistroBancarioService registroBancarioService;

	@GetMapping
	public ResponseEntity<Page<RegistroBancarioDto>> findAll(
			@PageableDefault(page=0, size=12, sort="dataHora", direction=Direction.DESC) Pageable paginacao) {

		Page<RegistroBancario> registrosBancarios = registroBancarioService.findAll(paginacao);

		return ResponseEntity.ok().body(registroBancarioService.convertToDto(registrosBancarios));
	}

	@GetMapping(value="/{idRegistroBancario}")
	public ResponseEntity<RegistroBancarioDto> findById(@PathVariable Long idRegistroBancario) {
		RegistroBancario registroBancario = registroBancarioService.findById(idRegistroBancario);

		return ResponseEntity.ok().body(new RegistroBancarioDto(registroBancario));
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody RegistroBancarioForm registroBancarioForm,
			UriComponentsBuilder uriBuilder) {

		RegistroBancario registroBancario = registroBancarioService.convertToEntity(registroBancarioForm);

		registroBancario = registroBancarioService.insert(registroBancario);

		URI	uri = uriBuilder.path("/registros-bancarios/{id}").buildAndExpand(registroBancario.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
