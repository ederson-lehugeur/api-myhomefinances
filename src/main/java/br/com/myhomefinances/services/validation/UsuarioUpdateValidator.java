package br.com.myhomefinances.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.UsuarioDTO;
import br.com.myhomefinances.repositories.UsuarioRepository;
import br.com.myhomefinances.resources.exception.FieldMessage;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void initialize(UsuarioUpdate ann) {}

	@Override
	public boolean isValid(UsuarioDTO objDto, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Usuario usuario = usuarioRepository.findByEmail(objDto.getEmail());

		if (usuario != null && !usuario.getId().equals(uriId)) {
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
