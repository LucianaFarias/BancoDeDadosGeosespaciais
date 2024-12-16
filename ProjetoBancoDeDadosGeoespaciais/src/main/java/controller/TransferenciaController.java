package controller;

import java.util.List;

import dao.EstoqueDAO;
import dao.TransferenciaDAO;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;
import dto.TransferenciaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import mappers.MapperTransferencia;
import model.Transferencia;

public class TransferenciaController {

    private TransferenciaDAO dao;
    
	private EntityManagerFactory factory;

   
	public List<TransferenciaDTO> listarTransferenciasPorFilial(FilialDTO filialDTO) throws Exception {
        return dao.buscarTransferenciasPorFilial(filialDTO);      		      		
    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
        EntityManager entityManager = factory.createEntityManager();
        MapperTransferencia mapper = new MapperTransferencia();
        try {
            entityManager.getTransaction().begin();
            Transferencia transferencia = mapper.toEntity(dto);
            entityManager.persist(transferencia);

            EstoqueDAO estoqueDAO = new EstoqueDAO(factory);
            
            ProdutoDTO produto = dto.getProduto();
            int quantidade = dto.getQuantidade();
            
            // Buscar o estoque da filial de origem
            EstoqueDTO estoqueOrigem = estoqueDAO.buscarEstoquesDaFilial(dto.getOrigem()).stream()
                    .filter(estoque -> estoque.getProduto().getId() == produto.getId())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Produto não encontrado na filial de origem"));

            // Atualizar o estoque da filial de origem
            estoqueOrigem.setQuantidade(estoqueOrigem.getQuantidade() - quantidade);
            estoqueDAO.atualizarEstoque(estoqueOrigem);

            // Buscar o estoque da filial de destino
            EstoqueDTO estoqueDestino = estoqueDAO.buscarEstoquesDaFilial(dto.getDestino()).stream()
                    .filter(estoque -> estoque.getProduto().getId() == produto.getId())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Produto não encontrado na filial de destino"));

            // Atualizar o estoque da filial de destino
            estoqueDestino.setQuantidade(estoqueDestino.getQuantidade() + quantidade);
            estoqueDAO.atualizarEstoque(estoqueDestino);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
    
    public void gerarRelatorioTransferenciasPorFilial(TransferenciaDTO dto){
        MapperTransferencia mapper = new MapperTransferencia();
        List<TransferenciaDTO> transferencias;
		transferencias = dao.ListarTransferenciaPorFilial(dto);
        System.out.println("Relatório de Movimentações de Estoque:");
        for (TransferenciaDTO transferencia : transferencias) {
            System.out.println("ID: " + transferencia.getId() +
                               ", Produto: " + transferencia.getProduto().getNome() +
                               ", Origem: " + transferencia.getOrigem().getNome() +
                               ", Destino: " + transferencia.getDestino().getNome() +
                               ", Quantidade: " + transferencia.getQuantidade());
        }
    }
}