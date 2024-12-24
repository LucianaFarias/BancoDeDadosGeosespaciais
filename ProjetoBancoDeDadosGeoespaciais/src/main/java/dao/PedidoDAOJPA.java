package dao;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import dto.PedidoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperPedido;
import model.Estoque;
import model.Pedido;

public class PedidoDAOJPA implements IPedidoDAO{
	
	private EntityManagerFactory entityManagerFactory;
	private MapperPedido mapper;

	public PedidoDAOJPA() {
		this.entityManagerFactory = ConexaoJPA.getInstancia().getFactory();
		this.mapper = new MapperPedido();
	}

	@Override
	public List<PedidoDTO> listarPedidos() throws Exception{
		EntityManager em = entityManagerFactory.createEntityManager();

		List<Pedido> resultList;
		try {
			em.getTransaction().begin();
			TypedQuery<Pedido> query = em.createQuery(
					"SELECT p FROM Pedido p", Pedido.class);
			
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

	@Override
	public PedidoDTO buscarPedidoPorId(PedidoDTO pedido) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Pedido pedidoEncontrado = entityManager.find(Pedido.class, pedido.getId());
			pedido = mapper.toDTO(pedidoEncontrado);
			entityManager.getTransaction().commit();
			return pedido;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

}