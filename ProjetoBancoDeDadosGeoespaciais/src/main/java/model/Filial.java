package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Filial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column
    private String nome;

	@Column(name="localizacao")
    private Localizacao endereco;

    public Filial(int id, String nome, Localizacao endereco) {
        this.id = id;
        this.nome = nome;
        this.setEndereco(endereco);
    }

    public Filial() {

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

	public Localizacao getEndereco() {
		return endereco;
	}

	public void setEndereco(Localizacao endereco) {
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