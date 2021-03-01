package br.com.renanfretta.ve.votoeletronico.configs;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mysql.cj.exceptions.MysqlErrorNumbers;

import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessagesProperty messagesProperty;

	@ExceptionHandler({ ErroTratadoRestException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(ErroTratadoRestException ex, WebRequest request) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(NoSuchElementException ex, WebRequest request) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		Erro erro = getErroComTratamentoMySQL(ex);
		List<Erro> erros = Arrays.asList(erro);
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private Erro getErroComTratamentoMySQL(RuntimeException ex) {
		ConstraintViolationException constraintViolationException = getConstraintViolationException(ex);
		
		SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException = getSQLIntegrityConstraintViolationException(ex);

		if (sqlIntegrityConstraintViolationException != null) {

			String constraintName = constraintViolationException.getConstraintName();

			if (sqlIntegrityConstraintViolationException.getErrorCode() == MysqlErrorNumbers.ER_ROW_IS_REFERENCED_2) {
				// SQLSTATE: 23000 Message: Cannot delete or update a parent row: a foreign key
				// constraint fails (%s)
				return new Erro(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__EXISTEM_REGISTROS_DEPENDENTES), sqlIntegrityConstraintViolationException.getMessage());

			} else if (sqlIntegrityConstraintViolationException.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
				// SQLSTATE: 23000 Message: Duplicate entry '%s' for key %d
				MessagesPropertyEnum messagesPropertyEnum = MessagesPropertyEnum.ERRO__DUPLICIDADE_BANCO_DADOS;

				if (constraintName != null) {
					if (constraintName.equalsIgnoreCase("uc_voto_pauta_usuario"))
						messagesPropertyEnum = MessagesPropertyEnum.RN__UNIQUE_VOTO_USUARIO;
				}
				return new Erro(messagesProperty.getMessage(messagesPropertyEnum), sqlIntegrityConstraintViolationException.getMessage());
			}
		}
		
		return new Erro(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__INTEGRIDADE_BANCO_DADOS), ExceptionUtils.getRootCauseMessage(ex));
	}

	private ConstraintViolationException getConstraintViolationException(RuntimeException ex) {
		if (ex.getCause() instanceof ConstraintViolationException)
			return (ConstraintViolationException) ex.getCause();
		return null;
	}

	private SQLIntegrityConstraintViolationException getSQLIntegrityConstraintViolationException(RuntimeException ex) {
		Throwable rootEx = ExceptionUtils.getRootCause(ex);
		if (rootEx instanceof SQLIntegrityConstraintViolationException)
			return (SQLIntegrityConstraintViolationException) rootEx;
		return null;
	}

	@Getter
	@AllArgsConstructor
	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

	}

}