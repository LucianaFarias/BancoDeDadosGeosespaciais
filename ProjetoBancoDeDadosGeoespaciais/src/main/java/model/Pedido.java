package model;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
    private int id;
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens;
	
	@Column(name = "entrega")
    private Localizacao localDeEntrega;
	
	@Column(name = "origem")
    private Localizacao origemDoPedido;

    public Pedido(int id, List<ItemPedido> itens, Localizacao localDeEntrega, Localizacao origemDoPedido) {
		this.id = id;
		this.itens = itens;
		this.localDeEntrega = localDeEntrega;
		this.origemDoPedido = origemDoPedido;
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

