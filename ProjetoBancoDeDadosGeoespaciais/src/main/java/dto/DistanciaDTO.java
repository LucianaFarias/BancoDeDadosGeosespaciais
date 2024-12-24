package dto;

public class DistanciaDTO {
	
	private FilialDTO filial1;
	
	private FilialDTO filial2;
	
	private double distancia;

	public DistanciaDTO(FilialDTO filial1, FilialDTO filial2, double distancia) {
		this.filial1 = filial1;
		this.filial2 = filial2;
		this.distancia = distancia;
	}
	
	public DistanciaDTO() {
		
	}

	public FilialDTO getFilial1() {
		return filial1;
	}

	public void setFilial1(FilialDTO filial1) {
		this.filial1 = filial1;
	}

	public FilialDTO getFilial2() {
		return filial2;
	}

	public void setFilial2(FilialDTO filial2) {
		this.filial2 = filial2;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
}
