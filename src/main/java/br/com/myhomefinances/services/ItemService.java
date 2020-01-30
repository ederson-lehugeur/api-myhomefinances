package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.ItemDTO;
import br.com.myhomefinances.repositories.ItemRepository;
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

	public Item find(Integer id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Item> item = itemRepository.findByIdAndUsuarioId(id, user.getId());

		return item.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Item.class.getName()));
	}

	public List<Item> findByUsuario() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		List<Item> listaItens = itemRepository.findByUsuarioId(user.getId());

		return listaItens;
	}

	public Page<Item> findPageByUsuario(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return itemRepository.findByUsuarioId(user.getId(), pageRequest);
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

		try {
			itemRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item que esteja"
					+ " associado a algum registro.");
		}
	}

	public Item fromDTO(ItemDTO itemDto) {
		Categoria categoria = categoriaService.find(itemDto.getCategoriaId());

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		return new Item(itemDto.getId(), itemDto.getNome(), itemDto.getComplemento(),
				categoria, usuario);
	}

	private void updateData(Item novoItem, Item item) {
		novoItem.setNome(item.getNome());
		novoItem.setComplemento(item.getComplemento());
		novoItem.setDataHora(item.getDataHora());
		novoItem.setCategoria(item.getCategoria());
	}

}
