package model;

public class Estoque {
    private Filial filial;
    private Produto produto;
    private int quantidade;

    public Estoque(int id, Filial filial, Produto produto, int quantidade) {
        this.filial = filial;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    public Filial getFilial() { 
    	return filial; 
    	}
    public void setFilial(Filial filial) { 
    	this.filial = filial; 
    	}
    public Produto getProduto() { 
    	return produto; 
    	}
    public void setProduto(Produto produto) { 
    	this.produto = produto; 
    	}
    public int getQuantidade() { 
    	return quantidade; 
    	}
    public void setQuantidade(int quantidade) { 
    	this.quantidade = quantidade; 
    	}
}
