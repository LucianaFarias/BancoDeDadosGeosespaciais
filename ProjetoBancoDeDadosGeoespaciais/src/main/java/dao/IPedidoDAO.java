package dao;

import java.util.List;

import dto.PedidoDTO;

public interface IPedidoDAO {
	
	public List<PedidoDTO> listarPedidos() throws Exception;

}
