package model;

import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private Localizacao localDeEntrega;
    private Localizacao origemDoPedido;

    public Pedido(int id , List<ItemPedido> itens) {
        this.id = id;
        this.itens = itens;
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

