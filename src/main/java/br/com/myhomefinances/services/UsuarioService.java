package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.UsuarioDTO;
import br.com.myhomefinances.repositories.UsuarioRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> findAll() {
		List<Usuario> listaUsuarios = usuarioRepository.findAll();

		return listaUsuarios;
	}

	public Usuario find(Integer id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return usuario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Usuario.class.getName()));
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return usuarioRepository.findAll(pageRequest);
	}

	public Usuario insert(Usuario usuario) {
		usuario.setId(null);

		usuario = usuarioRepository.save(usuario);

		return usuario;
	}

	public Usuario update(Usuario usuario) {
		Usuario novoUsuario = find(usuario.getId());

		updateData(novoUsuario, usuario);

		return usuarioRepository.save(novoUsuario);
	}

	public void delete(Integer id) {
		find(id);

		usuarioRepository.deleteById(id);
	}

	public Usuario fromDTO(UsuarioDTO usuarioDto) {
		return new Usuario(usuarioDto.getId(), usuarioDto.getNome(), usuarioDto.getSobrenome(),
				usuarioDto.getEmail(), usuarioDto.getSenha());
	}

	private void updateData(Usuario novoUsuario, Usuario usuario) {
		novoUsuario.setNome(usuario.getNome());
		novoUsuario.setSobrenome(usuario.getSobrenome());
		novoUsuario.setEmail(usuario.getEmail());
		novoUsuario.setSenha(usuario.getSenha());
	}

}
