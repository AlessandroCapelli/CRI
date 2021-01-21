package it.CroceRossaItaliana.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Sede non presente")
public class SedeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -2754394839471847399L;

	public SedeNotFoundException() {
		super();
	}

	public SedeNotFoundException(String messaggio, Throwable causa) {
		super(messaggio, causa);
	}

	public SedeNotFoundException(String messaggio) {
		super(messaggio);
	}

	public SedeNotFoundException(Throwable causa) {
		super(causa);
	}

}