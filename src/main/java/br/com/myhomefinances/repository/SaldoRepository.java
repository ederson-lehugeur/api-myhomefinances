package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {

	List<Saldo> findByUsuario(Usuario usuario);

	Optional<Saldo> findByIdAndUsuario(Long id, Usuario usuario);

	Saldo findFirstByUsuarioOrderByDataHoraDesc(Usuario usuario);

}
