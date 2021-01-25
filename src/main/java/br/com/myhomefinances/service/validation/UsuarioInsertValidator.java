package br.com.myhomefinances.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.UsuarioRepository;
import br.com.myhomefinances.resource.exception.FieldMessage;
import br.com.myhomefinances.resource.form.UsuarioNewForm;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewForm> {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void initialize(UsuarioInsert ann) {}

	@Override
	public boolean isValid(UsuarioNewForm objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Optional<Usuario> usuario = usuarioRepository.findByEmail(objDto.getEmail());

		if (usuario.isPresent()) {
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
