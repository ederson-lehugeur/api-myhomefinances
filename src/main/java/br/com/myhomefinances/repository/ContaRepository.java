package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	List<Conta> findAllByUsuario(Usuario usuario);

	Optional<Conta> findByIdAndUsuario(Long id, Usuario usuario);

	Page<Conta> findAllByUsuario(Usuario usuario, Pageable pageRequest);

}
