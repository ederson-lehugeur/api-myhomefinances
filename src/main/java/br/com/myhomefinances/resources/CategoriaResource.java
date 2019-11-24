package br.com.myhomefinances.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.dto.CategoriaDTO;
import br.com.myhomefinances.services.CategoriaService;

@RestController
@RequestMapping(value="categorias")
public class CategoriaResource {

	@Autowired
	CategoriaService categoriaService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<Categoria> listaCategoria = categoriaService.findAll();
		List<CategoriaDTO> listaCategoriaDTO = listaCategoria.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listaCategoriaDTO);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		Categoria categoria = categoriaService.find(id);

		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Categoria> listaCategoria = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listaCategoriaDTO = listaCategoria.map(categoria -> new CategoriaDTO(categoria));

		return ResponseEntity.ok().body(listaCategoriaDTO);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Categoria> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {

		Categoria categoria = categoriaService.fromDTO(categoriaDTO);

		categoria = categoriaService.insert(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> insert(@PathVariable Integer id,
			@Valid @RequestBody CategoriaDTO categoriaDTO) {

		Categoria categoria = categoriaService.fromDTO(categoriaDTO);

		categoria.setId(id);
		categoria = categoriaService.update(categoria);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		categoriaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
