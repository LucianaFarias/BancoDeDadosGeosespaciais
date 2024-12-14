package dto;

import org.locationtech.jts.geom.Point;

public class LocalizacaoDTO {
	
	private String id;
	private String estado;
	private String cidade;
	private double latitude;
	private double longitude;
	private Point ponto;

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Point getPonto() {
		return ponto;
	}
	public void setPonto(Point ponto) {
		this.ponto = ponto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalizacaoDTO(String id, String estado, String cidade, double latitude, double longitude, Point ponto) {
		this.id = id;
		this.estado = estado;
		this.cidade = cidade;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ponto = ponto;
	}

	public LocalizacaoDTO() {
	}
}
