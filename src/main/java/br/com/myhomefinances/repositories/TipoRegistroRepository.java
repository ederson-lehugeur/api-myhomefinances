package br.com.myhomefinances.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.TipoRegistro;

@Repository
public interface TipoRegistroRepository extends JpaRepository<TipoRegistro, Integer> {

	@Transactional(readOnly=true)
	Optional<TipoRegistro> findByNome(String nome);
}
