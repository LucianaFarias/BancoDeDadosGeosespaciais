package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.FilialDTO;
import dto.TransferenciaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperTransferencia;
import model.Transferencia;

public class TransferenciaDAOJPA implements ITransferenciaDAO {

    private static TransferenciaDAOJPA instance;
    private EntityManagerFactory factory;

    // Construtor privado para garantir que a instância seja criada apenas uma vez
    private TransferenciaDAOJPA() {
        this.factory = ConexaoJPA.getInstancia().getFactory();
    }

    // Método para obter a instância única do DAO (Singleton)
    public static synchronized TransferenciaDAOJPA getInstance() {
        if (instance == null) {
            instance = new TransferenciaDAOJPA();
        }
        return instance;
    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            MapperTransferencia mapper = new MapperTransferencia();
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
        try {
            em.getTransaction().begin();
            Transferencia transferencia = em.find(Transferencia.class, dto.getId());
            if (transferencia == null) {
                throw new IllegalArgumentException("Transferência não encontrada com o ID: " + dto.getId());
            }
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
        try {
            em.getTransaction().begin();
            Transferencia transferencia = em.find(Transferencia.class, dto.getId());
            if (transferencia == null) {
                throw new IllegalArgumentException("Transferência não encontrada com o ID: " + dto.getId());
            }
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
    
    public void excluirTransferencia(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Localiza a transferência pelo ID fornecido
            Transferencia transferencia = em.find(Transferencia.class, dto.getId());
            
            // Remove a transferência
            em.remove(transferencia);
            
            // Confirma a transação
            em.getTransaction().commit();
        } catch (Exception e) {
            // Em caso de erro, faz o rollback
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }


    public List<TransferenciaDTO> buscarTransferenciasPorFilial(FilialDTO filial) throws Exception {
        EntityManager em = factory.createEntityManager();
        List<TransferenciaDTO> transferenciasDTO = new ArrayList<>();
        try {
            TypedQuery<Transferencia> query = em.createQuery(
                "SELECT t FROM Transferencia t WHERE t.origem.id = :filialId OR t.destino.id = :filialId",
                Transferencia.class
            );
            query.setParameter("filialId", filial.getId());
            List<Transferencia> transferencias = query.getResultList();
            MapperTransferencia mapper = new MapperTransferencia();
            for (Transferencia transferencia : transferencias) {
                transferenciasDTO.add(mapper.toDTO(transferencia));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return transferenciasDTO;
    }
    
    public TransferenciaDTO buscarTransferenciaPorId(TransferenciaDTO dto) throws Exception {
        EntityManager em = factory.createEntityManager();
        TransferenciaDTO transferenciasDTO = new TransferenciaDTO();
        try {
        	em.getTransaction().begin();
        	Transferencia transferencia = em.find(Transferencia.class, dto.getId());
            MapperTransferencia mapper = new MapperTransferencia();
            dto = mapper.toDTO(transferencia);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return dto;
    }

    public List<TransferenciaDTO> listarTransferenciasPorOrigem(FilialDTO origem) throws Exception {
        List<TransferenciaDTO> transferencias = buscarTransferenciasPorFilial(origem);
        return transferencias.stream()
                .filter(dto -> dto.getOrigem() != null && dto.getOrigem().getId() == origem.getId())
                .collect(Collectors.toList());
    }
}
