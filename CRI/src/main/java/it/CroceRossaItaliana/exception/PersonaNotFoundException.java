package it.CroceRossaItaliana.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Persona non presente")
public class PersonaNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8252953614783756469L;

  public PersonaNotFoundException() {
    super();
  }

  public PersonaNotFoundException(String messaggio, Throwable causa) {
    super(messaggio, causa);
  }

  public PersonaNotFoundException(String messaggio) {
    super(messaggio);
  }

  public PersonaNotFoundException(Throwable causa) {
    super(causa);
  }
  
}
