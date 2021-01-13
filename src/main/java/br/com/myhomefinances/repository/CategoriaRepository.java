package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByIdAndUsuarioId(Long idCategoria, Long idUsuario);

	List<Categoria> findByUsuarioId(Long idUsuario);

	Page<Categoria> findByUsuarioId(Long idUsuario, Pageable pageRequest);

	void deleteByIdAndUsuarioId(Long idCategoria, Long idUsuario);

}
