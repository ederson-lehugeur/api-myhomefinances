package br.com.myhomefinances.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.UsuarioDto;
import br.com.myhomefinances.form.UsuarioNewForm;
import br.com.myhomefinances.form.UsuarioUpdateForm;
import br.com.myhomefinances.repository.UsuarioRepository;
import br.com.myhomefinances.service.email.EmailService;
import br.com.myhomefinances.service.exception.AuthorizationException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;
import br.com.myhomefinances.service.exception.ResetTokenNotFoundException;

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

	public Page<Usuario> findAll(Pageable paginacao) {
		Page<Usuario> usuariosPage = usuarioRepository.findAll(paginacao);

		return usuariosPage;
	}

	public Usuario findById(Long id) {
		Usuario usuarioAutenticado = UsuarioService.authenticated();

		if (usuarioAutenticado == null || (!usuarioAutenticado.hasRole("ROLE_ADMIN") && !id.equals(usuarioAutenticado.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return usuario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado",
				Usuario.class.getName()));
	}

	public Usuario findByEmail(String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			new ObjectNotFoundException("E-mail não encontrado");
		}

		return usuario.get();
	}

	public Usuario findByResetToken(String token) {
		Optional<Usuario> usuario = usuarioRepository.findByResetToken(token);

		return usuario.orElseThrow(() -> new ResetTokenNotFoundException("Token não encontrado"));
	}

	public Usuario insert(Usuario usuario) {
		usuario.setId(null);

		usuario = usuarioRepository.save(usuario);

		Saldo saldo = new Saldo(null, 0.0, new Date(), usuario);

		saldoService.insert(saldo);

		emailService.sendConfirmationHtmlEmail(usuario);

		return usuario;
	}

	public Usuario update(Usuario usuario) {
		Usuario usuarioAutenticado = UsuarioService.authenticated();

		if (usuarioAutenticado == null || !usuario.getId().equals(usuarioAutenticado.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario novoUsuario = findById(usuario.getId());

		updateData(novoUsuario, usuario);

		return usuarioRepository.save(novoUsuario);
	}

	public Usuario updateUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public void delete(Long id) {
		findById(id);

		usuarioRepository.deleteById(id);
	}

	public Usuario convertToEntity(UsuarioNewForm usuarioNewForm) {
		return new Usuario(null, usuarioNewForm.getNome(), usuarioNewForm.getSobrenome(),
				usuarioNewForm.getEmail(), bCryptPasswordEncoder.encode(usuarioNewForm.getSenha()));
	}

	public Usuario convertToEntity(UsuarioUpdateForm usuarioUpdateForm) {
		return new Usuario(null, usuarioUpdateForm.getNome(),
				usuarioUpdateForm.getSobrenome(), usuarioUpdateForm.getEmail());
	}

	public List<UsuarioDto> convertToDto(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
	}

	public Page<UsuarioDto> convertToDto(Page<Usuario> usuariosPage) {
		return usuariosPage.map(UsuarioDto::new);
	}

	private void updateData(Usuario novoUsuario, Usuario usuario) {
		novoUsuario.setNome(usuario.getNome());
		novoUsuario.setSobrenome(usuario.getSobrenome());
		novoUsuario.setEmail(usuario.getEmail());
		novoUsuario.setSenha(usuario.getSenha());
	}

	public static Usuario authenticated() {
		try {
			return (Usuario) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
