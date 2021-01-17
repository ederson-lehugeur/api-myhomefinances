package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.CategoriaDto;
import br.com.myhomefinances.form.CategoriaForm;
import br.com.myhomefinances.repository.CategoriaRepository;
import br.com.myhomefinances.service.exception.DataIntegrityException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	UsuarioService usuarioService;

	public List<Categoria> findAll() {
		Usuario usuario = UsuarioService.authenticated();

		List<Categoria> categorias = categoriaRepository.findByUsuarioId(usuario.getId());

		return categorias;
	}

	public Categoria findById(Long id) {
		Usuario usuario = UsuarioService.authenticated();

		Optional<Categoria> categoria = categoriaRepository.findByIdAndUsuarioId(id, usuario.getId());

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

	public void delete(Long id) {
		findById(id);

		Usuario usuario = UsuarioService.authenticated();

		try {
			categoriaRepository.deleteByIdAndUsuarioId(id, usuario.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui itens.");
		}
	}

	public Categoria convertToEntity(CategoriaForm categoriaForm) {
		Usuario usuario = UsuarioService.authenticated();

		return new Categoria(null, categoriaForm.getNome(),
				categoriaForm.getComplemento(), usuario);
	}

	public List<CategoriaDto> convertToDto(List<Categoria> categorias) {
		return categorias.stream().map(CategoriaDto::new).collect(Collectors.toList());
	}

	public Page<CategoriaDto> convertToDto(Page<Categoria> categoriasPage) {
		return categoriasPage.map(CategoriaDto::new);
	}

	private void updateData(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
		novaCategoria.setComplemento(categoria.getComplemento());
		novaCategoria.setUsuario(categoria.getUsuario());
	}

}
