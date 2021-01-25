package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.repository.CategoriaRepository;
import br.com.myhomefinances.resource.dto.CategoriaDto;
import br.com.myhomefinances.resource.form.CategoriaForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	UsuarioService usuarioService;

	public Page<Categoria> findAll(Pageable paginacao) {
		Page<Categoria> categorias = categoriaRepository.findAll(paginacao);

		return categorias;
	}

	public Categoria findById(Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Categoria.class.getName()));
	}

	public Categoria findByNome(String nome) {
		Optional<Categoria> categoria = categoriaRepository.findByNome(nome);

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

		categoriaRepository.deleteById(id);
	}

	public Categoria convertToEntity(CategoriaForm categoriaForm) {
		return new Categoria(null, categoriaForm.getNome(),
				categoriaForm.getComplemento());
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
	}

}
