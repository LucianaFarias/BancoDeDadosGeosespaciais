package dao;

import java.util.ArrayList;
import java.util.List;

import dto.PedidoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mappers.MapperPedido;
import model.Pedido;

public class PedidoDAO implements IPedidoDAO{
	
	private EntityManagerFactory entityManagerFactory;
	private MapperPedido mapper;

	public PedidoDAO() {
		this.entityManagerFactory = Conexao.getInstancia().getFactory();
		this.mapper = new MapperPedido();
	}

	@Override
	public List<PedidoDTO> listarPedidos() throws Exception{
		EntityManager em = entityManagerFactory.createEntityManager();

		List<Pedido> resultList;
		try {
			em.getTransaction().begin();
			TypedQuery<Pedido> query = em.createQuery(
					"SELECT e FROM Pedido", Pedido.class);

			resultList = query.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
	    	throw e;
	    }finally {
	    	em.close();
		}
		
		List<PedidoDTO> pedidos = new ArrayList<>();
		for(Pedido pedido: resultList) {
			pedidos.add(mapper.toDTO(pedido));
		}
		return pedidos;
	}

}
