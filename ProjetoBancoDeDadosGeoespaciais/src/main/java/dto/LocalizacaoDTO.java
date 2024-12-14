package dto;

public class LocalizacaoDTO {
	private String estado;
	private String cidade;
	private double latitude;
    private double longitude;
    
    public LocalizacaoDTO(String estado,String cidade,double latitude,double longitude) {
    	this.estado=estado;
    	this.cidade=cidade;
    	this.latitude=latitude;
    	this.longitude=longitude;
    	
    }
    public LocalizacaoDTO() {
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

}
