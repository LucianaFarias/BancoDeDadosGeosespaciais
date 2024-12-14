package dao;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mappers.MapperTransferencia;
import dto.FilialDTO;
import dto.TransferenciaDTO;
import model.Transferencia;

public class TransferenciaDAO {

    private EntityManagerFactory factory;
    
    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
    	EntityManager em = factory.createEntityManager();
    	MapperTransferencia mapper = new MapperTransferencia();
    	try {
    		em.getTransaction().begin();
    		Transferencia transferencia = mapper.toEntity(dto);
    		em.persist(transferencia);
    		em.getTransaction().commit();
    	} catch (Exception e) {
    		em.getTransaction().rollback();
    		throw e;
    	} finally {
    		em.close();
    	}
    }

    public List<TransferenciaDTO> buscarTransferenciasPorFilial(FilialDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        List<Transferencia> resultList;
        try {
            em.getTransaction().begin();
            TypedQuery<Transferencia> query = em.createQuery(
                    "SELECT t FROM Transferencia t WHERE t.origem.id = :filialId OR t.destino.id = :filialId", 
                    Transferencia.class);
            query.setParameter("filialId", dto.getId());
            resultList = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

        List<TransferenciaDTO> transferencias = new ArrayList<>();
        MapperTransferencia mapper = new  MapperTransferencia();
        for (Transferencia transferencia : resultList) {
            transferencias.add(mapper.toDTO(transferencia));
        }
        return transferencias;
    }
}