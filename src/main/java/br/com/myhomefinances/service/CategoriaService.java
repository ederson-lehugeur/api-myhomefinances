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
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.CategoriaDto;
import br.com.myhomefinances.form.CategoriaForm;
import br.com.myhomefinances.repository.CategoriaRepository;
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

	public List<Categoria> findAll() {
		UserDetailsSpringSecurity user = getUserAuthenticated();

		List<Categoria> categorias = categoriaRepository.findByUsuarioId(user.getId());

		return categorias;
	}

	public Categoria findById(Long id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		Optional<Categoria> categoria = categoriaRepository.findByIdAndUsuarioId(id, user.getId());

		return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Categoria.class.getName()));
	}

	public Page<Categoria> findPageable(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		return categoriaRepository.findByUsuarioId(user.getId(), pageRequest);
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

	public void delete(Long id) {
		findById(id);

		UserDetailsSpringSecurity user = UserService.authenticated();

		try {
			categoriaRepository.deleteByIdAndUsuarioId(id, user.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui itens.");
		}
	}

	public Categoria convertToCategoria(CategoriaForm categoriaForm) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		Usuario usuario = usuarioService.find(user.getId());

		return new Categoria(null, categoriaForm.getNome(),
				categoriaForm.getComplemento(), usuario);
	}

	public List<CategoriaDto> convertToCategoriaDto(List<Categoria> categorias) {
		return categorias.stream().map(CategoriaDto::new).collect(Collectors.toList());
	}

	public Page<CategoriaDto> convertToCategoriaDto(Page<Categoria> categoriasPage) {
		return categoriasPage.map(CategoriaDto::new);
	}

	private void updateData(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
		novaCategoria.setComplemento(categoria.getComplemento());
		novaCategoria.setUsuario(categoria.getUsuario());
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
