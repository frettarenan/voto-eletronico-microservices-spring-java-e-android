package br.com.renanfretta.ve.votoeletronico.exceptions;

import lombok.Getter;

@Getter
public class ErroTratadoRestException extends Exception {

	private static final long serialVersionUID = -514047279231560994L;
	
	public ErroTratadoRestException(String message) {
		super(message);
	}

}