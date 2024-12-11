package model;

public class Filial {
    private int id;
    private String nome;
    private Localizaçao endereco;
    

    public Filial(int id, String nome, String cidade, Localizaçao endereco) {
        this.id = id;
        this.nome = nome;
        this.setEndereco(endereco);
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

	public Localizaçao getEndereco() {
		return endereco;
	}

	public void setEndereco(Localizaçao endereco) {
		this.endereco = endereco;
	}
	
	@Override
    public String toString() {
        return "Filial{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereço='" + endereco + '\'' +
                '}';
    }
}