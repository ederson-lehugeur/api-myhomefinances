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
	public ResponseEntity<List<CategoriaDto>> findAll() {
		List<Categoria> categorias = categoriaService.findAll();

		return ResponseEntity.ok().body(categoriaService.convertToDto(categorias));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<CategoriaDto> findById(@PathVariable Long id) {
		Categoria categoria = categoriaService.findById(id);

		return ResponseEntity.ok().body(new CategoriaDto(categoria));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CategoriaDto> insert(@Valid @RequestBody CategoriaForm categoriaForm,
			UriComponentsBuilder uriBuilder) {

		Categoria categoria = categoriaService.convertToEntity(categoriaForm);

		categoria = categoriaService.insert(categoria);

		URI	uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<CategoriaDto> update(@PathVariable Long id,
			@Valid @RequestBody CategoriaForm categoriaForm) {

		Categoria categoria = categoriaService.convertToEntity(categoriaForm);

		categoria.setId(id);
		categoria = categoriaService.update(categoria);

		return ResponseEntity.ok(new CategoriaDto(categoria));
	}

	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoriaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
