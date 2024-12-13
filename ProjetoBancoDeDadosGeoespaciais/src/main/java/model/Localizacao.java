package model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import lombok.Getter;
import lombok.Setter;

public class Localizacao {
	private String estado;
	private String cidade;
	private double latitude;
	private double longitude;
	private Point ponto;

	public Localizacao(String estado, String cidade, double latitude, double longitude) {
		this.estado = estado;
		this.cidade = cidade;
		this.latitude = latitude;
		this.longitude = longitude;
		criarPonto(longitude, latitude);

	}

	public void criarPonto(double longitude, double latitude) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate coordenada = new Coordinate(longitude, latitude);
		Point ponto = factory.createPoint(coordenada);
		setPonto(ponto);
	}

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

}
