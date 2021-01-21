package it.CroceRossaItaliana.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sede")
public class Sede  implements Serializable {

	private static final long serialVersionUID = -6812826807094828675L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "citta", nullable = false)
	private String citta;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sede")
    private Set<Veicolo> mezzi;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "sedi")
    private Set<Persona> persone;
	
	protected Sede() {
		
	}
	
	public Sede(String nome, String citta) {
		this.nome = nome;
		this.citta = citta;
		this.persone = new HashSet<Persona>();
	}

	@Override
	public String toString() {
		return String.format("Sede[id=%d, nome='%s', citta='%s']", id, nome, citta);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public Set<Persona> getPersone() {
		return persone;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public void setPersone(Set<Persona> persone) {
		if(persone != null) {
			this.persone = persone;
			
			for(Persona persona: persone) {
				persona.addSede(this);
			}
		}
	}
	
	public void addPersona(Persona persona) {
		if(persona != null) {
			this.persone.add(persona);
		}
	}
	
	public void removePersona(Persona persona) {
		if(persona != null) {
			this.persone.remove(persona);
		}
	}
	
	public Set<Veicolo> getMezzi() {
		return this.mezzi;
	}
	
	public void addVeicolo(Veicolo veicolo) {
		if(veicolo != null) {
			this.mezzi.add(veicolo);
		}
	}
	
	public void removeVeicolo(Veicolo veicolo) {
		if(veicolo != null) {
			this.mezzi.remove(veicolo);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sede other = (Sede) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
