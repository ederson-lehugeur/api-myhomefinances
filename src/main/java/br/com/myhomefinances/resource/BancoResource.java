package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.dto.BancoDto;
import br.com.myhomefinances.form.BancoForm;
import br.com.myhomefinances.service.BancoService;

@RestController
@RequestMapping(value="bancos")
public class BancoResource {

	@Autowired
	BancoService bancoService;

	@GetMapping
	public ResponseEntity<List<BancoDto>> findAll() {
		List<Banco> bancos = bancoService.findAll();

		return ResponseEntity.ok().body(bancoService.convertEntityToDto(bancos));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<BancoDto> findById(@PathVariable Long id) {
		Banco banco = bancoService.findById(id);

		return ResponseEntity.ok().body(new BancoDto(banco));
	}

	@GetMapping(value="/pageable")
	public ResponseEntity<Page<BancoDto>> findPageable(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Banco> bancos = bancoService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(bancoService.convertEntityToDto(bancos));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<BancoDto> insert(@Valid @RequestBody BancoForm bancoForm,
			UriComponentsBuilder uriBuilder) {

		Banco banco = bancoService.convertFormToEntity(bancoForm);

		banco = bancoService.insert(banco);

		URI	uri = uriBuilder.path("/bancos/{id}").buildAndExpand(banco.getId()).toUri();

		return ResponseEntity.created(uri).body(new BancoDto(banco));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<BancoDto> update(@PathVariable Long id,
			@Valid @RequestBody BancoForm bancoForm) {

		Banco banco = bancoService.convertFormToEntity(bancoForm);

		banco.setId(id);
		banco = bancoService.update(banco);

		return ResponseEntity.ok(new BancoDto(banco));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bancoService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
