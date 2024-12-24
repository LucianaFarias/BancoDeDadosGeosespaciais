package dao;

import java.util.ArrayList;
import java.util.List;

import dto.DistanciaDTO;
import dto.FilialDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperDistancia;
import model.Distancia;

public class DistanciaDAO implements IDistanciaDAO{
	
	private EntityManagerFactory factory;
	private MapperDistancia mapper;
	
	public DistanciaDAO() {
		this.factory = Conexao.getInstancia().getFactory();
		this.mapper = new MapperDistancia();
	}
	
	public List<DistanciaDTO> buscarDistanciasDaFilial(FilialDTO filial) throws Exception{
		EntityManager em = factory.createEntityManager();

		List<Distancia> resultList;
	    try {
	        em.getTransaction().begin();
	        TypedQuery<Distancia> query = em.createQuery(
	                "SELECT d FROM Distancia d WHERE d.filial1.id = :filialId OR d.filial2.id = :filialId ORDER BY distancia", Distancia.class);
	        query.setParameter("filialId", filial.getId());

	        resultList = query.getResultList();
	        em.getTransaction().commit();
	        
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	    
	    List<DistanciaDTO> distancias = new ArrayList<>();
		for(Distancia distancia: resultList) {
			distancias.add(mapper.toDTO(distancia));
		}

		return distancias;
	}

}
