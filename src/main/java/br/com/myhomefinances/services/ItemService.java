package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.repositories.ItemRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	public List<Item> findAll() {
		List<Item> listaItens = itemRepository.findAll();

		return listaItens;
	}

	public Item find(Integer id) {
		Optional<Item> item = itemRepository.findById(id);

		return item.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! "
				+ "Id: " + id + ", Tipo: " + Item.class.getName()));
	}

}
