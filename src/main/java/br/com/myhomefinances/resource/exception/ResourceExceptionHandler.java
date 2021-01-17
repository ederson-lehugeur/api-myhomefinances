package br.com.myhomefinances.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.myhomefinances.service.exception.AuthorizationException;
import br.com.myhomefinances.service.exception.DataIntegrityException;
import br.com.myhomefinances.service.exception.IncorrectPasswordException;
import br.com.myhomefinances.service.exception.InvalidPerfilException;
import br.com.myhomefinances.service.exception.NegativeBalanceException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;
import br.com.myhomefinances.service.exception.ResetTokenNotFoundException;
import br.com.myhomefinances.service.exception.TokenExpiredException;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new ObjectNotFoundError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Object not found", e.getMessage(),
				request.getRequestURI(), e.getEntity());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Data integrity", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", e.getMessage(),
				request.getRequestURI());

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(NegativeBalanceException.class)
	public ResponseEntity<StandardError> negativeBalance(NegativeBalanceException e,
			HttpServletRequest request) {

		StandardError err = new NegativeBalanceError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Negative balance error", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(InvalidPerfilException.class)
	public ResponseEntity<StandardError> invalidPerfil(InvalidPerfilException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Invalid perfil", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.FORBIDDEN.value(), "Authorization error", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(ResetTokenNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ResetTokenNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new ResetTokenNotFoundError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Token not found", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<StandardError> invalidPerfil(IncorrectPasswordException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Incorrect password", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<StandardError> invalidPerfil(TokenExpiredException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Token expired", e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
