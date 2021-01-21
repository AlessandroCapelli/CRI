package it.CroceRossaItaliana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "pilota")
@PrimaryKeyJoinColumn(name = "pilotaId")
public class Pilota extends Persona {
	
	private static final long serialVersionUID = -1867930796386416302L;
	
	@Column(name = "patente", nullable = false)
	private String patente;
	
	protected Pilota() {
		
	}
	
	public Pilota(String nome, String cognome, String patente) {
		super(nome, cognome);
		this.patente = patente;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

}
