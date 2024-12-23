package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    private int id;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
    private Produto produto;
    private int quantidade;

    public ItemPedido(int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    public ItemPedido() {
    	
    }
  
    //Retorna a quantidade de cada produto que falta em vários estoques
    public List<EstoqueDTO> faltaEstoque(List<EstoqueDTO> estoques){
        List<EstoqueDTO> resultado = new ArrayList<EstoqueDTO>();
    	for(EstoqueDTO estoqueDTO : estoques){
            if((estoqueDTO.getProduto().getId() == produto.getId()) &&
                estoqueDTO.getQuantidade()<quantidade){
                EstoqueDTO estoqueQueFalta = estoqueDTO;
                estoqueQueFalta.setQuantidade(quantidade - estoqueDTO.getQuantidade());
                resultado.add(estoqueQueFalta);
            }
        }
        return resultado;
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
