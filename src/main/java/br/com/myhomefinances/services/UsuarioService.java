package br.com.myhomefinances.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.UsuarioNewDTO;
import br.com.myhomefinances.dto.UsuarioUpdateDTO;
import br.com.myhomefinances.repositories.UsuarioRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PerfilService perfilService;

	@Autowired
	SaldoService saldoService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<Usuario> findAll() {
		List<Usuario> listaUsuarios = usuarioRepository.findAll();

		return listaUsuarios;
	}

	public Usuario find(Integer id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null || (!user.hasRole("ROLE_ADMIN") && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return usuario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado",
				Usuario.class.getName()));
	}

	public Usuario findByEmail(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario == null) {
			new ObjectNotFoundException("E-mail não encontrado");
		}

		return usuario;
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return usuarioRepository.findAll(pageRequest);
	}

	@Transactional
	public Usuario insert(Usuario usuario) {
		usuario.setId(null);

		usuario = usuarioRepository.save(usuario);

		Saldo saldo = new Saldo(null, 0.0, new Date(), usuario);

		saldoService.insert(saldo);

		emailService.sendConfirmationHtmlEmail(usuario);

		return usuario;
	}

	public Usuario update(Usuario usuario) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null || !usuario.getId().equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario novoUsuario = find(usuario.getId());

		updateData(novoUsuario, usuario);

		return usuarioRepository.save(novoUsuario);
	}

	public Usuario updatePasswordForgot(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public void delete(Integer id) {
		find(id);

		usuarioRepository.deleteById(id);
	}

	public Usuario fromNewDTO(UsuarioNewDTO usuarioNewDto) {
		return new Usuario(usuarioNewDto.getId(), usuarioNewDto.getNome(), usuarioNewDto.getSobrenome(),
				usuarioNewDto.getEmail(), bCryptPasswordEncoder.encode(usuarioNewDto.getSenha()));
	}

	public Usuario fromUpdateDTO(UsuarioUpdateDTO usuarioUpdateDto) {
		return new Usuario(usuarioUpdateDto.getId(), usuarioUpdateDto.getNome(),
				usuarioUpdateDto.getSobrenome(), usuarioUpdateDto.getEmail());
	}

	private void updateData(Usuario novoUsuario, Usuario usuario) {
		novoUsuario.setNome(usuario.getNome());
		novoUsuario.setSobrenome(usuario.getSobrenome());
		novoUsuario.setEmail(usuario.getEmail());
		novoUsuario.setSenha(usuario.getSenha());
	}

}
