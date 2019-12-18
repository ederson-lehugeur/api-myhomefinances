package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

	@Transactional(readOnly=true)
	List<Registro> findByUsuario(Usuario usuario);

	@Transactional(readOnly=true)
	Optional<Registro> findByIdAndUsuario(Integer id, Usuario usuario);

	@Transactional(readOnly=true)
	Page<Registro> findByUsuario(Usuario usuario, Pageable pageRequest);
}
