package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Integer> {

	@Transactional(readOnly=true)
	Saldo findFirstByUsuarioOrderByDataHoraDesc(Usuario usuario);
}
