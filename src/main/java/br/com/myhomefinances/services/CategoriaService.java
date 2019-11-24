package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Categoria;
import br.com.myhomefinances.repositories.CategoriaRepository;
import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		List<Categoria> listaCategorias = categoriaRepository.findAll();

		return listaCategorias;
	}

	public Categoria find(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);

		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());

		return categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		find(id);

		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui itens.");
		}
	}
}
