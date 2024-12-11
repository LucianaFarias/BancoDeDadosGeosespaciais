package model;

public class ItemPedido {
    private int id;
    private Produto produto;
    private int quantidade;

    public ItemPedido(int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getId() { 
    	return id; 
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
