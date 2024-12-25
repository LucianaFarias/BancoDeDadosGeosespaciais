package model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    private int id;
    private String nome;

    public Produto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public Produto() {
    	
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
}
