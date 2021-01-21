package it.CroceRossaItaliana.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Veicolo non presente")
public class VeicoloNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4183810976537689127L;

	public VeicoloNotFoundException() {
		super();
	}

	public VeicoloNotFoundException(String messaggio, Throwable causa) {
		super(messaggio, causa);
	}

	public VeicoloNotFoundException(String messaggio) {
		super(messaggio);
	}

	public VeicoloNotFoundException(Throwable causa) {
		super(causa);
	}
	
}