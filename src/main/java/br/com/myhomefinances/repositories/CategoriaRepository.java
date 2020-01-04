package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT c FROM Categoria c WHERE c.id = ?1 AND c.usuario.id = ?2")
	Optional<Categoria> findByIdAndUsuario(Integer idCategoria, Integer idUsuario);

	@Transactional(readOnly=true)
	@Query("SELECT c FROM Categoria c WHERE c.usuario.id = ?1")
	List<Categoria> findByUsuario(Integer idUsuario);

	@Transactional(readOnly=true)
	@Query("SELECT c FROM Categoria c WHERE c.usuario.id = ?1")
	Page<Categoria> findByUsuario(Integer idUsuario, Pageable pageRequest);

	void deleteByIdAndUsuarioId(Integer idCategoria, Integer idUsuario);
}
