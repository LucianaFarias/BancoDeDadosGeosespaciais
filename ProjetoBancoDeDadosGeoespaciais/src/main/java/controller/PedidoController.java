package controller;

import java.util.ArrayList;
import java.util.List;

import dao.IPedidoDAO;
import dao.PedidoDAO;
import dto.PedidoDTO;
import model.StatusPedido;

public class PedidoController {
	
	private IPedidoDAO pedidoDAO;
	
	public PedidoController() {
		this.setPedidoDAO(new PedidoDAO());
	}
	
	public List<PedidoDTO> listarPedidos() throws Exception{
		return pedidoDAO.listarPedidos();
	}
	
	public List<PedidoDTO> listarPedidosPendentes() throws Exception{
		List<PedidoDTO> pendentes = new ArrayList<>();
		for(PedidoDTO pedido: listarPedidos()) {
			if(!pedido.getStatus().equals(StatusPedido.CONCLUIDO)) {
				pendentes.add(pedido);
			}
		}
		return pendentes;
	}
	
	public PedidoDTO buscarPedidoPorId(PedidoDTO pedido) throws Exception {
		return pedidoDAO.buscarPedidoPorId(pedido);
	}

	public IPedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(IPedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

}
