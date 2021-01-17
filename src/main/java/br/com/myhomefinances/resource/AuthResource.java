package br.com.myhomefinances.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.form.ForgotPasswordForm;
import br.com.myhomefinances.form.ResetPasswordForm;
import br.com.myhomefinances.security.JWTUtil;
import br.com.myhomefinances.service.AuthService;
import br.com.myhomefinances.service.UsuarioService;

@RestController
@RequestMapping(value="auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@PostMapping(value="/refreshToken")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Usuario user = UsuarioService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
	}

	@PostMapping(value="/forgotPassword")
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordForm forgotPasswordDto) {
		authService.forgotPassword(forgotPasswordDto.getEmail());

		return ResponseEntity.noContent().build();
	}

	@PostMapping(value="/resetPassword")
	public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordForm resetPasswordDto,
			@RequestParam(value="token", required=true) String token) {

		authService.resetPassword(token, resetPasswordDto.getSenha(), resetPasswordDto.getConfirmacaoSenha());

		return ResponseEntity.noContent().build();
	}

}
