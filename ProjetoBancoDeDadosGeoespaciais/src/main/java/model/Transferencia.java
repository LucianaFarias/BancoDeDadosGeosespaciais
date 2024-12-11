package model;

public class Transferencia {
    private int id;
    private Produto produto;
    private Filial origem;
    private Filial destino;
    private int quantidade;

    public Transferencia(int id, Produto produto, Filial origem, Filial destino, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.origem = origem;
        this.destino = destino;
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
    public Filial getOrigem() { 
    	return origem; 
    	}
    public void setOrigem(Filial origem) { 
    	this.origem = origem; 
    	}
    public Filial getDestino() { 
    	return destino; 
    	}
    public void setDestino(Filial destino) { 
    	this.destino = destino; 
    	}
    public int getQuantidade() { 
    	return quantidade;
    	}
    public void setQuantidade(int quantidade) { 
    	this.quantidade = quantidade; 
    	}
}
