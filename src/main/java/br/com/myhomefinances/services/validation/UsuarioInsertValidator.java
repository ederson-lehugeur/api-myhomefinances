package br.com.myhomefinances.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.form.UsuarioNewForm;
import br.com.myhomefinances.repository.UsuarioRepository;
import br.com.myhomefinances.resources.exception.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewForm> {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void initialize(UsuarioInsert ann) {}

	@Override
	public boolean isValid(UsuarioNewForm objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Usuario usuario = usuarioRepository.findByEmail(objDto.getEmail());

		if (usuario != null) {
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
