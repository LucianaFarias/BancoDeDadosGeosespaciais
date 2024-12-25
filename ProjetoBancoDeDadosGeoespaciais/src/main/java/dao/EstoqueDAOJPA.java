package dao;

import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperEstoque;
import model.Estoque;

public class EstoqueDAOJPA implements IEstoqueDAO{

	private EntityManagerFactory factory;
	private MapperEstoque mapper;
	
	public EstoqueDAOJPA() {
		this.factory = ConexaoJPA.getInstancia().getFactory();
		this.mapper = new MapperEstoque();
	}

	public List<EstoqueDTO> buscarEstoquesDaFilial(FilialDTO filial) throws Exception{
		EntityManager em = factory.createEntityManager();

		List<Estoque> resultList;
		try {
			em.getTransaction().begin();
			TypedQuery<Estoque> query = em.createQuery(
					"SELECT e FROM Estoque e WHERE e.filial.id = :filialId AND e.quantidade > 0", Estoque.class);
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
	                "SELECT e FROM Estoque e WHERE e.produto.id = :produtoId AND e.quantidade > 0", Estoque.class);
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
	    for (Estoque estoque : resultList) {
	        estoques.add(mapper.toDTO(estoque));
	    }

	    return estoques;
	}

	public void atualizarEstoque(EstoqueDTO estoque) throws Exception {
	    EntityManager entityManager = factory.createEntityManager();
	    try {
	        entityManager.getTransaction().begin();
	        Estoque estoqueEncontrado = entityManager.find(Estoque.class, estoque.getId());
	        estoqueEncontrado = mapper.toEntity(estoque);
	        entityManager.merge(estoqueEncontrado);
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        entityManager.getTransaction().rollback();
	        throw e;
	    } finally {
	        entityManager.close();
	    }
	}
	
	@Override
	public List<EstoqueDTO> listarEstoques() throws Exception {
		EntityManager em = factory.createEntityManager();

	    List<Estoque> resultList;
	    try {
	        em.getTransaction().begin();
	        TypedQuery<Estoque> query = em.createQuery(
	                "SELECT e FROM Estoque e", Estoque.class);

	        resultList = query.getResultList();
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }

	    List<EstoqueDTO> estoques = new ArrayList<>();
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
