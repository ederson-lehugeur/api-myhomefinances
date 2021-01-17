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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Page<ItemDto>> findAll(
			@PageableDefault(page=0, size=12, sort="nome", direction=Direction.ASC) Pageable paginacao) {

		Page<Item> itensPage = itemService.findAll(paginacao);

		return ResponseEntity.ok().body(itemService.convertToDto(itensPage));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ItemDto> findById(@PathVariable Long id) {
		Item item = itemService.findById(id);

		return ResponseEntity.ok().body(new ItemDto(item));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ItemDto> insert(@Valid @RequestBody ItemForm itemForm,
			UriComponentsBuilder uriBuilder) {

		Item item = itemService.convertToEntity(itemForm);

		item = itemService.insert(item);

		URI	uri = uriBuilder.path("/itens/{id}").buildAndExpand(item.getId()).toUri();

		return ResponseEntity.created(uri).body(new ItemDto(item));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<ItemDto> update(@PathVariable Long id,
			@Valid @RequestBody ItemForm itemFormDto) {

		Item item = itemService.convertToEntity(itemFormDto);

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
