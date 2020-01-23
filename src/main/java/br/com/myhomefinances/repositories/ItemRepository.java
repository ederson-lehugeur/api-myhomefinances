package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Transactional(readOnly=true)
	Optional<Item> findByIdAndUsuarioId(Integer idCategoria, Integer idUsuario);

	@Transactional(readOnly=true)
	List<Item> findByUsuarioId(Integer idUsuario);

	@Transactional(readOnly=true)
	Page<Item> findByUsuarioId(Integer idUsuario, Pageable pageRequest);

}
