package dto;

public class EstoqueDTO {
	private FilialDTO filial;
    private ProdutoDTO produto;
    private int quantidade;

    public EstoqueDTO(FilialDTO filial, ProdutoDTO produto, int quantidade) {
        this.filial = filial;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    public EstoqueDTO() {
	}
	public FilialDTO getFilial() { 
    	return filial; 
    	}
    public void setFilial(FilialDTO filial) { 
    	this.filial = filial; 
    	}
    public ProdutoDTO getProduto() { 
    	return produto; 
    	}
    public void setProduto(ProdutoDTO produto) { 
    	this.produto = produto; 
    	}
    public int getQuantidade() { 
    	return quantidade; 
    	}
    public void setQuantidade(int quantidade) { 
    	this.quantidade = quantidade; 
    	}
}
