package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.dto.ItemDto;
import br.com.myhomefinances.form.ItemForm;
import br.com.myhomefinances.service.ItemService;

@RestController
@RequestMapping(value="itens")
public class ItemResource {

	@Autowired
	ItemService itemService;

	@GetMapping
	public ResponseEntity<List<ItemDto>> findAll() {
		List<Item> itens = itemService.findAll();

		return ResponseEntity.ok().body(itemService.convertToItemDto(itens));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ItemDto> findById(@PathVariable Long id) {
		Item item = itemService.findById(id);

		return ResponseEntity.ok().body(new ItemDto(item));
	}

	@GetMapping(value="/pageable")
	public ResponseEntity<Page<ItemDto>> findPageable(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="12") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Item> itensPage = itemService.findPageable(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(itemService.convertToItemDto(itensPage));
	}

	@PostMapping
	public ResponseEntity<ItemDto> insert(@Valid @RequestBody ItemForm itemForm,
			UriComponentsBuilder uriBuilder) {

		Item item = itemService.convertToItem(itemForm);

		item = itemService.insert(item);

		URI	uri = uriBuilder.path("/itens/{id}").buildAndExpand(item.getId()).toUri();

		return ResponseEntity.created(uri).body(new ItemDto(item));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<ItemDto> update(@PathVariable Long id,
			@Valid @RequestBody ItemForm itemFormDto) {

		Item item = itemService.convertToItem(itemFormDto);

		item.setId(id);
		item = itemService.update(item);

		return ResponseEntity.ok(new ItemDto(item));
	}

	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		itemService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
