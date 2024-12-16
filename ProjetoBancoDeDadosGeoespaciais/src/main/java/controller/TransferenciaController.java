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
    
    public TransferenciaController() {
    	this.dao = new TransferenciaDAO();
    }

   
	public List<TransferenciaDTO> listarTransferenciasPorFilial(FilialDTO filialDTO) throws Exception {
        return dao.buscarTransferenciasPorFilial(filialDTO);      		      		    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
    	dao.registrarTransferencia(dto);      
    }
    
    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
    	dao.registrarChegadaEstoque(dto);
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