package controller;

import java.util.ArrayList;
import java.util.List;

import dao.EstoqueDAO;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.PedidoDTO;
import mappers.MapperPedido;
import model.Pedido;

public class EstoqueController {
	
	private EstoqueDAO estoqueDAO;

	public void atenderPedido(FilialDTO filial, PedidoDTO pedido) {
		List<EstoqueDTO> estoquesQueFaltam = verificarSeFaltaEstoque(filial, pedido);
		if(estoquesQueFaltam.size()>0) {
			
		}else {
			//retorna lista vaizia de transferencias
		}
	}
	
	// Se o estoque for suficiente, a lista será vazia
	public List<EstoqueDTO> verificarSeFaltaEstoque(FilialDTO filial, PedidoDTO pedido) {
		List<EstoqueDTO> estoques;
		try {
			estoques = estoqueDAO.buscarEstoquesDaFilial(filial);
			MapperPedido mapper = new MapperPedido();
			Pedido pedidoEntidade = mapper.toEntity(pedido);
			return pedidoEntidade.faltaEstoque(estoques);
		} catch (Exception e) {
			System.out.print("Erro ao carregar dados");
			return null;
		}

	}
	
	public void buscarEstoquesMaisProximos(List<EstoqueDTO> itensQueFaltam) {
		List<EstoqueDTO> estoquesEncontrados = new ArrayList<>();
		for(EstoqueDTO estoque: itensQueFaltam) {
			try {
				estoquesEncontrados.addAll(estoqueDAO.buscarEstoquesDoProduto(estoque.getProduto()));
			} catch (Exception e) {
			}
		}
		
		//verificar proximidade
		
	}
}
