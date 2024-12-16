package dto;

import model.Produto;

public class ProdutoDTO {
	private int id;
	private String nome;

	public ProdutoDTO(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();  
		this.nome = produto.getNome(); 
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