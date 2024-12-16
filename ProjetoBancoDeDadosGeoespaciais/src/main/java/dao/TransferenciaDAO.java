package dao;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mappers.MapperTransferencia;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;
import dto.TransferenciaDTO;
import model.Transferencia;

public class TransferenciaDAO implements ITransferenciaDAO {

    private EntityManagerFactory factory;
  
	public void registrarTransferencia(TransferenciaDTO dto) throws Exception {

    public TransferenciaDAO() {
    	this.factory = Conexao.getInstancia().getFactory();
    }
    
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
    
    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
        EntityManager entityManager = factory.createEntityManager();
        MapperTransferencia mapper = new MapperTransferencia();
        EstoqueDAO estoqueDAO = new EstoqueDAO();

        try {
            entityManager.getTransaction().begin();

            // Converter e registrar a transferência como concluída
            Transferencia transferencia = mapper.toEntity(dto);
            transferencia.setConcluida(true);
            entityManager.merge(transferencia);

            // Obter o produto e a quantidade da transferência
            ProdutoDTO produto = dto.getProduto();
            int quantidade = dto.getQuantidade();

            // Buscar o estoque da filial de destino
            EstoqueDTO estoqueDestino = estoqueDAO.buscarEstoquesDaFilial(dto.getDestino()).stream()
                .filter(estoque -> estoque.getProduto().getId() == produto.getId())
                .findFirst()
                .orElseThrow(() -> new Exception("Produto não encontrado na filial de destino"));

            // Atualizar o estoque da filial de destino com a quantidade transferida
            estoqueDestino.setQuantidade(estoqueDestino.getQuantidade() + quantidade);
            estoqueDAO.atualizarEstoque(estoqueDestino);

            // Commit da transação
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
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
    public List<TransferenciaDTO> ListarTransferenciaPorFilial(TransferenciaDTO dto) {
        List<TransferenciaDTO> transferencias = null;
		try {
			transferencias = buscarTransferenciasPorFilial(dto.getOrigem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transferencias = buscarTransferenciasPorFilial(dto.getOrigem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<TransferenciaDTO> result = new ArrayList<>();

        for (TransferenciaDTO transferenciaDTO : transferencias) {
            if (transferenciaDTO.getOrigem().equals(dto.getOrigem())) {
                result.add(transferenciaDTO);
            }
        }
        return result;
    }
}