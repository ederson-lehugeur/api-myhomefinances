package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.dto.ItemDTO;
import br.com.myhomefinances.repositories.ItemRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoriaService categoriaService;

	public List<Item> findAll() {
		List<Item> listaItens = itemRepository.findAll();

		return listaItens;
	}

	public Item find(Integer id) {
		Optional<Item> item = itemRepository.findById(id);

		return item.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Item.class.getName()));
	}

	public Page<Item> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return itemRepository.findAll(pageRequest);
	}

	public Item insert(Item item) {
		item.setId(null);

		item = itemRepository.save(item);

		return item;
	}

	public Item update(Item item) {
		Item novoItem = find(item.getId());

		updateData(novoItem, item);

		return itemRepository.save(novoItem);
	}

	public void delete(Integer id) {
		find(id);

		itemRepository.deleteById(id);
	}

	public Item fromDTO(ItemDTO itemDto) {
		Categoria categoria = categoriaService.find(itemDto.getCategoriaId());

		return new Item(itemDto.getId(), itemDto.getNome(), itemDto.getComplemento(), categoria);
	}

	private void updateData(Item novoItem, Item item) {
		novoItem.setNome(item.getNome());
		novoItem.setComplemento(item.getComplemento());
		novoItem.setCategoria(item.getCategoria());
	}

}
