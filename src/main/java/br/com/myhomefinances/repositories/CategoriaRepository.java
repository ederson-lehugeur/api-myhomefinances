package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	@Transactional(readOnly=true)
	Optional<Categoria> findByIdAndUsuarioId(Integer idCategoria, Integer idUsuario);

	@Transactional(readOnly=true)
	List<Categoria> findByUsuarioId(Integer idUsuario);

	@Transactional(readOnly=true)
	Page<Categoria> findByUsuarioId(Integer idUsuario, Pageable pageRequest);

	void deleteByIdAndUsuarioId(Integer idCategoria, Integer idUsuario);
}
