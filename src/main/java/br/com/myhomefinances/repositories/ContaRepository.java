package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

	@Transactional(readOnly=true)
	List<Conta> findAllByUsuario(Usuario usuario);

	@Transactional(readOnly=true)
	Optional<Conta> findByIdAndUsuario(Integer id, Usuario usuario);

	@Transactional(readOnly=true)
	Page<Conta> findAllByUsuario(Usuario usuario, Pageable pageRequest);
}
