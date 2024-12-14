package model;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private Localizacao localDeEntrega;
    private Localizacao origemDoPedido;

    public Pedido(int id , List<ItemPedido> itens) {
        this.id = id;
        this.itens = itens;
    }

    public Pedido() {
    	
    };
    
    // Gera uma lista com o que falta nos estoques para atender o pedido
    // Se não faltar nada, retorna uma lista vazia
    public List<EstoqueDTO> faltaEstoque(List<EstoqueDTO> estoques){
        List<EstoqueDTO> resultado = new ArrayList<EstoqueDTO>();
    	for(ItemPedido item : itens){
           resultado.addAll(item.faltaEstoque(estoques));
        }
        return resultado;
    }
    
    public int getId() { 
    	return id; 
    	}
    public List<ItemPedido> getItens() { 
    	return itens; 
    	}
    public void setItens(List<ItemPedido> itens) { 
    	this.itens = itens; 
    	}

	public Localizacao getLocalDeEntrega() {
		return localDeEntrega;
	}

	public void setLocalDeEntrega(Localizacao localDeEntrega) {
		this.localDeEntrega = localDeEntrega;
	}

	public Localizacao getOrigemDoPedido() {
		return origemDoPedido;
	}

	public void setOrigemDoPedido(Localizacao origemDoPedido) {
		this.origemDoPedido = origemDoPedido;
	}
}

