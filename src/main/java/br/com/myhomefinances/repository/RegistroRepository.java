package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Usuario;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

	List<Registro> findByUsuario(Usuario usuario);

	Optional<Registro> findByIdAndUsuario(Long id, Usuario usuario);

	Page<Registro> findByUsuario(Usuario usuario, Pageable pageRequest);

}
