package dto;


public class FilialDTO {
	private int id;
	private String nome;
	private LocalizacaoDTO endereco;
	
	public void setId(int id) {
		this.id=id;
	}
	public int getId() {
    	return id; 
    }
    public String getNome() { 
    	return nome; 
    	}
    public void setNome(String nome) { 
    	this.nome = nome; 
    	}

	public LocalizacaoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(LocalizacaoDTO endereco) {
		this.endereco = endereco;
	}

}
