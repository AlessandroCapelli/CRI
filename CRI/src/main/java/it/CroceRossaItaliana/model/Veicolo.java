package it.CroceRossaItaliana.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "veicolo")
public class Veicolo  implements Serializable {

	private static final long serialVersionUID = -2976977849848575304L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;
	@Column(name = "numero_posti", nullable = false)
	private int numeroPosti;
	@Column(name = "targa", nullable = false)
	private String targa;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="sede_id")
    private Sede sede;
	
	protected Veicolo() {
		
	}
	
	public Veicolo(int numeroPosti, String targa) {
		this.numeroPosti = numeroPosti;
		this.targa = targa;
	}
	
	@Override
	public String toString() {
		if(sede != null)
			return String.format("Veicolo[id=%d, numeroPosti='%s', targa='%s', sede='%s']", id, numeroPosti, targa, sede.toString());
		else
			return String.format("Veicolo[id=%d, numeroPosti='%s', targa='%s']", id, numeroPosti, targa);
	}
	
	public Long getId() {
		return id;
	}
	
	public int getNumeroPosti() {
		return numeroPosti;
	}
	
	public String getTarga() {
		return targa;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}
	
	public void setTarga(String targa) {
		this.targa = targa;
	}
	
	public void setSede(Sede sede) {
		if(sede != null) {
			this.sede = sede;
			sede.addVeicolo(this);
		}
	}
	
	public Sede removeSede() {
		Sede temp = this.sede;
		this.sede = null;
		temp.removeVeicolo(this);
		
		return temp;
	}
	
	public Sede getSede() {
		return this.sede;
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
		Veicolo other = (Veicolo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
