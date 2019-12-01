package br.com.myhomefinances.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.NegativeBalanceException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new ObjectNotFoundError(HttpStatus.NOT_FOUND.value(),
				e.getMessage(), System.currentTimeMillis(), e.getId(), e.getEntidade());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> objectNotFound(DataIntegrityException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> objectNotFound(MethodArgumentNotValidException e,
			HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
				"Erro de validação", System.currentTimeMillis());

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(NegativeBalanceException.class)
	public ResponseEntity<StandardError> objectNotFound(NegativeBalanceException e,
			HttpServletRequest request) {

		StandardError err = new NegativeBalanceError(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
