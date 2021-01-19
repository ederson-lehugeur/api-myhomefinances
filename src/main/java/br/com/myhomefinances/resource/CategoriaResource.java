package br.com.myhomefinances.resource;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.dto.CategoriaDto;
import br.com.myhomefinances.form.CategoriaForm;
import br.com.myhomefinances.service.CategoriaService;

@RestController
@RequestMapping(value="categorias")
public class CategoriaResource {

	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<Page<CategoriaDto>> findAll(
			@PageableDefault(page=0, size=12, sort="nome", direction=Direction.ASC) Pageable paginacao) {

		Page<Categoria> categoriasPage = categoriaService.findAll(paginacao);

		return ResponseEntity.ok().body(categoriaService.convertToDto(categoriasPage));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<CategoriaDto> findById(@PathVariable Long id) {
		Categoria categoria = categoriaService.findById(id);

		return ResponseEntity.ok().body(new CategoriaDto(categoria));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	@Transactional
	public ResponseEntity<CategoriaDto> insert(@Valid @RequestBody CategoriaForm categoriaForm,
			UriComponentsBuilder uriBuilder) {

		Categoria categoria = categoriaService.convertToEntity(categoriaForm);

		categoria = categoriaService.insert(categoria);

		URI	uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<CategoriaDto> update(@PathVariable Long id,
			@Valid @RequestBody CategoriaForm categoriaForm) {

		Categoria categoria = categoriaService.convertToEntity(categoriaForm);

		categoria.setId(id);
		categoria = categoriaService.update(categoria);

		return ResponseEntity.ok(new CategoriaDto(categoria));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoriaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
