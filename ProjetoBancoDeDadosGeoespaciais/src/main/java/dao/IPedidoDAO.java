package dao;

import java.util.List;

import dto.PedidoDTO;

public interface IPedidoDAO {
	
	public List<PedidoDTO> listarPedidos() throws Exception;
	public PedidoDTO buscarPedidoPorId(PedidoDTO pedido) throws Exception;
	public PedidoDTO atualizar(PedidoDTO pedido) throws Exception;


}
