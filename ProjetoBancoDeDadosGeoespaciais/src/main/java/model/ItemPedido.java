package model;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;

public class ItemPedido {
    private int id;
    private Produto produto;
    private int quantidade;

    public ItemPedido(int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
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
