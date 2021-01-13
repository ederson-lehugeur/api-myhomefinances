package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.ItemDto;
import br.com.myhomefinances.form.ItemForm;
import br.com.myhomefinances.repository.ItemRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	UsuarioService usuarioService;

	public List<Item> findAll() {
		UserDetailsSpringSecurity user = getUserAuthenticated();

		List<Item> listaItens = itemRepository.findByUsuarioId(user.getId());

		return listaItens;
	}

	public Item findById(Long id) {
		UserDetailsSpringSecurity user = getUserAuthenticated();

		Optional<Item> item = itemRepository.findByIdAndUsuarioId(id, user.getId());

		return item.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Item.class.getName()));
	}

	public Page<Item> findPageable(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = getUserAuthenticated();

		return itemRepository.findByUsuarioId(user.getId(), pageRequest);
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

		// Melhorar essa parte
		try {
			itemRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item que esteja"
					+ " associado a algum registro.");
		}
	}

	public Item convertToItem(ItemForm itemForm) {
		UserDetailsSpringSecurity user = getUserAuthenticated();

		Categoria categoria = categoriaService.findById(itemForm.getCategoriaId());

		Usuario usuario = usuarioService.find(user.getId());

		return new Item(itemForm.getNome(), itemForm.getComplemento(),
				categoria, usuario);
	}

	public List<ItemDto> convertToItemDto(List<Item> itens) {
		return itens.stream().map(ItemDto::new).collect(Collectors.toList());
	}

	public Page<ItemDto> convertToItemDto(Page<Item> itensPage) {
		return itensPage.map(ItemDto::new);
	}

	private void updateData(Item novoItem, Item item) {
		novoItem.setNome(item.getNome());
		novoItem.setComplemento(item.getComplemento());
		novoItem.setDataHora(item.getDataHora());
		novoItem.setCategoria(item.getCategoria());
	}

	// Criar anotação para injetar usuário logado.
	private UserDetailsSpringSecurity getUserAuthenticated() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return user;
	}

}
