package dao;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import dto.TransferenciaDTO;
import model.Transferencia;
import mappers.MapperTransferencia;

public class TransferenciaDAO implements ITransferenciaDAO {

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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        MapperTransferencia mapper = new MapperTransferencia();
        try {
            em.getTransaction().begin();

            // Atualizar transferência como concluída
            Transferencia transferencia = mapper.toEntity(dto);
            transferencia.setConcluida(true);
            em.merge(transferencia);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void cancelarTransferencia(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        MapperTransferencia mapper = new MapperTransferencia();
        try {
        	em.getTransaction().begin();

            // Atualizar transferência como cancelada
            Transferencia transferencia = mapper.toEntity(dto);
            transferencia.setCancelada(true);
            em.merge(transferencia);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<TransferenciaDTO> buscarTransferenciasPorFilial(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        List<Transferencia> transferencias;
        try {
            TypedQuery<Transferencia> query = em.createQuery(
                "SELECT t FROM Transferencia t WHERE t.origem.id = :filialId OR t.destino.id = :filialId",
                Transferencia.class
            );
            query.setParameter("filialId", dto.getOrigem().getId());
            transferencias = query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }

        MapperTransferencia mapper = new MapperTransferencia();
        List<TransferenciaDTO> transferenciasDTO = new ArrayList<>();
        for (Transferencia transferencia : transferencias) {
            transferenciasDTO.add(mapper.toDTO(transferencia));
        }
        return transferenciasDTO;
    }

    public List<TransferenciaDTO> listarTransferenciasPorOrigem(TransferenciaDTO dto) throws Exception {
        List<TransferenciaDTO> transferencias = buscarTransferenciasPorFilial(dto);
        List<TransferenciaDTO> transferenciasFiltradas = new ArrayList<>();

        for (TransferenciaDTO dtoTransferencia : transferencias) {
            if (dtoTransferencia.getOrigem() != null && dtoTransferencia.getOrigem().getId() == dto.getOrigem().getId()) {
                transferenciasFiltradas.add(dtoTransferencia);
            }
        }
        return transferenciasFiltradas;
    }
}