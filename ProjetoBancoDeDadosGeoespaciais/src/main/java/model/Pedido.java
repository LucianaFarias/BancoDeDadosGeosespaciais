package model;

import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private Localizaçao localDeEntrega;
    private Localizaçao origemDoPedido;

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

	public Localizaçao getLocalDeEntrega() {
		return localDeEntrega;
	}

	public void setLocalDeEntrega(Localizaçao localDeEntrega) {
		this.localDeEntrega = localDeEntrega;
	}

	public Localizaçao getOrigemDoPedido() {
		return origemDoPedido;
	}

	public void setOrigemDoPedido(Localizaçao origemDoPedido) {
		this.origemDoPedido = origemDoPedido;
	}
}

