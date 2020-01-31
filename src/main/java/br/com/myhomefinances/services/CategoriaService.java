package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.CategoriaDTO;
import br.com.myhomefinances.repositories.CategoriaRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	UsuarioService usuarioService;

	public List<Categoria> find() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		List<Categoria> listaCategorias = categoriaRepository.findByUsuarioId(user.getId());

		return listaCategorias;
	}

	public Page<Categoria> find(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return categoriaRepository.findByUsuarioId(user.getId(), pageRequest);
	}

	public Categoria findById(Integer id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Categoria> categoria = categoriaRepository.findByIdAndUsuarioId(id, user.getId());

		return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);

		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria novaCategoria = findById(categoria.getId());

		updateData(novaCategoria, categoria);

		return categoriaRepository.save(novaCategoria);
	}

	@Transactional
	public void delete(Integer id) {
		findById(id);

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		try {
			categoriaRepository.deleteByIdAndUsuarioId(id, user.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui itens.");
		}
	}

	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		return new Categoria(categoriaDto.getId(), categoriaDto.getNome(),
				categoriaDto.getComplemento(), usuario);
	}

	private void updateData(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
		novaCategoria.setComplemento(categoria.getComplemento());
		novaCategoria.setUsuario(categoria.getUsuario());
	}

}
