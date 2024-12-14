package dao;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mappers.MapperEstoque;
import model.Estoque;

public class EstoqueDAO implements IEstoqueDAO{

	private EntityManagerFactory factory;
	
	public List<EstoqueDTO> buscarEstoquesDaFilial(FilialDTO filial) throws Exception{
		EntityManager em = factory.createEntityManager();

		List<Estoque> resultList;
		try {
			em.getTransaction().begin();
			TypedQuery<Estoque> query = em.createQuery(
					"SELECT e FROM Estoque e WHERE e.filial.id = :filialId", Estoque.class);
			query.setParameter("filialId", filial.getId());

			resultList = query.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
	    	throw e;
	    }finally {
	    	em.close();
		}
		
		List<EstoqueDTO> estoques = new ArrayList<>();
		MapperEstoque mapper = new MapperEstoque();
		for(Estoque estoque: resultList) {
			estoques.add(mapper.toDTO(estoque));
		}

		// process the resultList as needed
		return estoques;
	}
	
	public List<EstoqueDTO> buscarEstoquesDoProduto(ProdutoDTO produto) throws Exception {
	    EntityManager em = factory.createEntityManager();

	    List<Estoque> resultList;
	    try {
	        em.getTransaction().begin();
	        TypedQuery<Estoque> query = em.createQuery(
	                "SELECT e FROM Estoque e WHERE e.produto.id = :produtoId", Estoque.class);
	        query.setParameter("produtoId", produto.getId());

	        resultList = query.getResultList();
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }

	    List<EstoqueDTO> estoques = new ArrayList<>();
	    MapperEstoque mapper = new MapperEstoque();
	    for (Estoque estoque : resultList) {
	        estoques.add(mapper.toDTO(estoque));
	    }

	    return estoques;
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

}
