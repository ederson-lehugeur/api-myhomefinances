package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	List<Conta> findAllByUsuarioId(Long idUsuario);

	Optional<Conta> findByIdAndUsuarioId(Long id, Long idUsuario);

	Page<Conta> findAllByUsuarioId(Long idUsuario, Pageable pageRequest);

}
