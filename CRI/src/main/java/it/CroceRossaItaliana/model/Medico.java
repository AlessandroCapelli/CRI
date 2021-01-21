package it.CroceRossaItaliana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "medico")
@PrimaryKeyJoinColumn(name = "medicoId")
public class Medico extends Persona {

	private static final long serialVersionUID = -7871432671312980996L;
	
	@Column(name = "specializzazione", nullable = false)
	private String specializzazione;
	
	protected Medico() {
		
	}
	
	public Medico(String nome, String cognome, String specializzazione) {
		super(nome, cognome);
		this.specializzazione = specializzazione;
	}

	public String getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}
	
}
