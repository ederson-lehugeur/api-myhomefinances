package br.com.myhomefinances.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.dto.ForgotPasswordDTO;
import br.com.myhomefinances.dto.ResetPasswordDTO;
import br.com.myhomefinances.security.JWTUtil;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.AuthService;
import br.com.myhomefinances.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@RequestMapping(value="/refreshToken", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserDetailsSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDto) {
		authService.forgotPassword(forgotPasswordDto.getEmail());

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDto,
			@RequestParam(value="token", required=true) String token) {

		authService.resetPassword(token, resetPasswordDto.getSenha(), resetPasswordDto.getConfirmacaoSenha());

		return ResponseEntity.noContent().build();
	}
}
