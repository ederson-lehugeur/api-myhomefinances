package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByIdAndUsuarioId(Long idCategoria, Long idUsuario);

	List<Item> findByUsuarioId(Long idUsuario);

	List<Item> findByUsuarioIdOrderByCategoriaNomeAscNomeAsc(Long idUsuario);

	Page<Item> findByUsuarioId(Long idUsuario, Pageable pageRequest);

}
