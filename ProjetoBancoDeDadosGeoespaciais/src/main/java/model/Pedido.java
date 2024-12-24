package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import dto.EstoqueDTO;

public class Pedido implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

    private int id;
    private List<ItemPedido> itens = new ArrayList<>();
    private Localizacao localDeEntrega;
    private Localizacao origemDoPedido;
	private Filial filialResponsavel;
	private Cliente cliente;
	private StatusPedido status;
	
    public Pedido(int id, List<ItemPedido> itens, Localizacao localDeEntrega, Localizacao origemDoPedido, Filial filialResponsavel, Cliente cliente, StatusPedido status) {
		this.id = id;
		this.itens = itens;
		this.localDeEntrega = localDeEntrega;
		this.origemDoPedido = origemDoPedido;
		this.filialResponsavel = filialResponsavel;
		this.cliente = cliente;
		this.status = status;
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

	public Filial getFilialResponsavel() {
		return filialResponsavel;
	}

	public void setFilialResponsavel(Filial filialResponsavel) {
		this.filialResponsavel = filialResponsavel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
}

