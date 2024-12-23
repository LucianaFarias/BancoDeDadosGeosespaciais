package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
    private int id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private List<ItemPedido> itens = new ArrayList<>();
	
	@Transient
    private Localizacao localDeEntrega;
	
	@Transient
    private Localizacao origemDoPedido;
	
	@ManyToOne
	@JoinColumn(name = "filial_id")
	private Filial filialResponsavel;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Enumerated(EnumType.STRING)
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

