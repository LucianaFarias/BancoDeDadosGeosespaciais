package controller;

import java.util.List;

import dao.IPedidoDAO;
import dao.PedidoDAO;
import dto.PedidoDTO;

public class PedidoController {
	
	private IPedidoDAO pedidoDAO;
	
	public PedidoController() {
		this.setPedidoDAO(new PedidoDAO());
	}
	
	public List<PedidoDTO> listarPedidos() throws Exception{
		return pedidoDAO.listarPedidos();
	}

	public IPedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(IPedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

}
