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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persona")
public class Persona implements Serializable {

	private static final long serialVersionUID = -5621256701597295456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "turno",
    		   joinColumns = @JoinColumn(name="persona_id"),
    		   inverseJoinColumns = @JoinColumn(name="sede_id"))
    private Set<Sede> sedi;
	
	@ManyToOne()
	@JoinColumn(name="responsabile_id")
	private Persona responsabile;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="responsabile")
	private Set<Persona> subordinati;
	
	protected Persona() {
		
	}
	
	public Persona(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		subordinati = new HashSet<Persona>();
		sedi = new HashSet<Sede>();
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}
	
	public Persona getResponsabile() {
		return responsabile;
	}
	
	public Set<Persona> getSubordinati() {
		return subordinati;
	}

	public Long getId() {
		return id;
	}
	
	public Set<Sede> getSedi() {
		return sedi;
	}
	
	@Override
	public String toString() {
		if(responsabile != null)
			// si sceglie di stampare l'eventuale ID del responsabile al posto di chiamare il suo toString() per evitare loop nel caso di responsabili ricorsivi
			return String.format("Persona[id=%d, nome='%s', cognome='%s', responsabile='%s']", id, nome, cognome, responsabile.getId());
		else
			return String.format("Persona[id=%d, nome='%s', cognome='%s']", id, nome, cognome);
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setResponsabile(Persona responsabile) {
		if(responsabile != null) {
			this.responsabile = responsabile;
			responsabile.addSubordinato(this);
		}
	}
	
	public void removeResponsabile() {
		this.responsabile.removeSubordinato(this);
		this.responsabile = null;
	}
	
	protected void addSubordinato(Persona subordinato) {
		if(subordinato != null)
			this.subordinati.add(subordinato);
	}
	
	protected void removeSubordinato(Persona subordinato) {
		if(subordinato != null)
			this.subordinati.remove(subordinato);
	}
	
	public void setSedi(Set<Sede> sedi) {
		if(sedi != null) {
			this.sedi = sedi;
			
			for(Sede sede: sedi) {
				sede.addPersona(this);
			}
		}
	}
	
	public void addSede(Sede sede) {
		if(sede != null) {
			this.sedi.add(sede);
			sede.addPersona(this);
		}
	}
	
	public void removeSede(Sede sede) {
		if(sede != null) {
			this.sedi.remove(sede);
			sede.removePersona(this);
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
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}