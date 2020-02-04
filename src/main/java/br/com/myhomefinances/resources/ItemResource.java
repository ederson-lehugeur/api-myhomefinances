package br.com.myhomefinances.resources;

import java.net.URI;
import java.util.List;

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

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.dto.ItemDTO;
import br.com.myhomefinances.services.ItemService;

@RestController
@RequestMapping(value="itens")
public class ItemResource {

	@Autowired
	ItemService itemService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Item> findById(@PathVariable Integer id) {

		Item item = itemService.findById(id);

		return ResponseEntity.ok().body(item);
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Item>> find() {

		List<Item> listaItens = itemService.find();

		return ResponseEntity.ok().body(listaItens);
	}

	@RequestMapping(value="/pageable", method=RequestMethod.GET)
	public ResponseEntity<Page<Item>> find(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="12") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Item> listaItens = itemService.find(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(listaItens);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ItemDTO itemDto) {

		Item item = itemService.fromDTO(itemDto);

		item = itemService.insert(item);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(item.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody ItemDTO itemDto) {

		Item item = itemService.fromDTO(itemDto);

		item.setId(id);
		item = itemService.update(item);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		itemService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
