package model;

import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "distancia")
public class Distancia {
	
	@OneToOne
	@JoinColumn(name = "filial_1")
	@EmbeddedId
	private Filial filial1;
	
	@OneToOne
	@JoinColumn(name = "filial_2")
	@EmbeddedId
	private Filial filial2;
	
	private double distancia;

	public Distancia(Filial filial1, Filial filial2, double distancia) {
		this.filial1 = filial1;
		this.filial2 = filial2;
		this.distancia = distancia;
	}

	public Distancia() {
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Distancia)) return false;
	    Distancia distancia = (Distancia) o;
	    return Double.compare(distancia.distancia, this.distancia) == 0 && Objects.equals(filial1, distancia.filial1) && Objects.equals(filial2, distancia.filial2);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(filial1, filial2, distancia);
	}

	public Filial getFilial1() {
		return filial1;
	}

	public void setFilial1(Filial filial1) {
		this.filial1 = filial1;
	}

	public Filial getFilial2() {
		return filial2;
	}

	public void setFilial2(Filial filial2) {
		this.filial2 = filial2;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
}
