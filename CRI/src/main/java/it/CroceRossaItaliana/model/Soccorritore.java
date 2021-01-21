package it.CroceRossaItaliana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "soccorritore")
@PrimaryKeyJoinColumn(name = "soccorritoreId")
public class Soccorritore extends Persona {

	private static final long serialVersionUID = 690170105706401762L;
	
	@Column(name = "ruolo", nullable = false)
	private String ruolo;
	
	protected Soccorritore() {
		
	}
	
	public Soccorritore(String nome, String cognome, String ruolo) {
		super(nome, cognome);
		this.ruolo = ruolo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}
