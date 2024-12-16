package model;

import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.TransferenciaDTO;
import exception.EstoqueInsuficienteException;
import mapper.MapperFilial;
import mappers.MapperEstoque;
import mappers.MapperProduto;

public class Estoque {
	private int id;
    private Filial filial;
    private Produto produto;
    private int quantidade;
   
    public Estoque(int id, Filial filial, Produto produto, int quantidade) {
		this.id = id;
		this.filial = filial;
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public Estoque() {
	}
    
    public TransferenciaDTO criarTransferencia(FilialDTO destino, int quantidadeTransferida) {
    	TransferenciaDTO transferencia = new TransferenciaDTO();   
    	MapperProduto mapperProduto = new MapperProduto();
    	transferencia.setDestino(destino);
    	transferencia.setOrigem(MapperFilial.toDTO(filial));
    	transferencia.setProduto(mapperProduto.toDTO(produto));
    	transferencia.setQuantidade(quantidadeTransferida);
    	
    	return transferencia;
    }
    
    public void remover(int quantidade) throws EstoqueInsuficienteException {
    	if(this.quantidade >= quantidade) {
    		this.quantidade = getQuantidade()-quantidade;
    	}else {
    		throw new EstoqueInsuficienteException("Estoque insuficiente");
    	}
    }
    
    public EstoqueDTO atualizarEstoque(List<TransferenciaDTO> transferencias) throws EstoqueInsuficienteException {
    	
    	for(TransferenciaDTO transferencia: transferencias) {
    		if(transferencia.getDestino().getId() == filial.getId() &&
    			transferencia.getProduto().getId() == produto.getId()) {
    			remover(transferencia.getQuantidade());
    		}
    	}
    	MapperEstoque mapperEstoque = new MapperEstoque();
    	EstoqueDTO estoqueAtualizado = mapperEstoque.toDTO(this);
    	return estoqueAtualizado;
    	
    }
    
	public Filial getFilial() { 
    	return filial; 
    	}
    public void setFilial(Filial filial) { 
    	this.filial = filial; 
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
