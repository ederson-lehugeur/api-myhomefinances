package br.com.myhomefinances.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Saldo;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {

	Optional<Saldo> findByUsuarioId(Long idUsuario);

}
