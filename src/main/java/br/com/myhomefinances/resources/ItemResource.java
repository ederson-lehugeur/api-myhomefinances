package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.services.ItemService;

@RestController
@RequestMapping(value="itens")
public class ItemResource {

	@Autowired
	ItemService itemService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		List<Item> listaItens = itemService.findAll();

		return ResponseEntity.ok().body(listaItens);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Item item = itemService.find(id);

		return ResponseEntity.ok().body(item);
	}

}
