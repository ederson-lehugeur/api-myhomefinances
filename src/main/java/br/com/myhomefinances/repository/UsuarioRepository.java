package br.com.myhomefinances.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	Optional<Usuario> findByResetToken(String resetToken);

}
