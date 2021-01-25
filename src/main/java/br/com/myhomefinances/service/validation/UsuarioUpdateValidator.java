package br.com.myhomefinances.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repository.UsuarioRepository;
import br.com.myhomefinances.resource.exception.FieldMessage;
import br.com.myhomefinances.resource.form.UsuarioUpdateForm;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioUpdateForm> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void initialize(UsuarioUpdate ann) {}

	@Override
	public boolean isValid(UsuarioUpdateForm objDto, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Long uriId = Long.parseLong(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Optional<Usuario> usuario = usuarioRepository.findByEmail(objDto.getEmail());

		if (usuario.isPresent() && !usuario.get().getId().equals(uriId)) {
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
