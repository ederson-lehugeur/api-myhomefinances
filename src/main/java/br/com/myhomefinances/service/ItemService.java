package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.ItemRepository;
import br.com.myhomefinances.resource.dto.ItemDto;
import br.com.myhomefinances.resource.form.ItemForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	UsuarioService usuarioService;

	public Page<Item> findAll(Pageable paginacao) {
		Usuario usuario = UsuarioService.authenticated();

		return itemRepository.findByUsuarioId(usuario.getId(), paginacao);
	}

	public Item findById(Long id) {
		Usuario usuario = UsuarioService.authenticated();

		Optional<Item> item = itemRepository.findByIdAndUsuarioId(id, usuario.getId());

		return item.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado",
				Item.class.getName()));
	}

	public Item insert(Item item) {
		return itemRepository.save(item);
	}

	public Item update(Item item) {
		Item novoItem = findById(item.getId());

		updateData(novoItem, item);

		return itemRepository.save(novoItem);
	}

	public void delete(Long id) {
		findById(id);

		itemRepository.deleteById(id);
	}

	public Item convertToEntity(ItemForm itemForm) {
		Usuario usuario = UsuarioService.authenticated();

		Categoria categoria = categoriaService.findById(itemForm.getCategoriaId());

		return new Item(itemForm.getNome(), itemForm.getComplemento(),
				categoria, usuario);
	}

	public List<ItemDto> convertToDto(List<Item> itens) {
		return itens.stream().map(ItemDto::new).collect(Collectors.toList());
	}

	public Page<ItemDto> convertToDto(Page<Item> itensPage) {
		return itensPage.map(ItemDto::new);
	}

	private void updateData(Item novoItem, Item item) {
		novoItem.setNome(item.getNome());
		novoItem.setComplemento(item.getComplemento());
		novoItem.setDataHoraCriacao(item.getDataHoraCriacao());
		novoItem.setCategoria(item.getCategoria());
	}

}
