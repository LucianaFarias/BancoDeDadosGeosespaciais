package controller;

import java.util.List;

import dao.EstoqueDAO;
import dao.IEstoqueDAO;
import dao.TransferenciaDAO;
import dto.TransferenciaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import mappers.MapperTransferencia;
import model.ItemPedido;
import model.Transferencia;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ItemPedidoDTO;
import dto.PedidoDTO;
import dto.ProdutoDTO;

public class TransferenciaController {

    private TransferenciaDAO dao;
    
    public TransferenciaController() {
    	this.dao = new TransferenciaDAO();
    }

    public List<TransferenciaDTO> listarTransferenciasPorFilial(FilialDTO dto) throws Exception {
        return dao.buscarTransferenciasPorFilial(dto);      		      		
    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
    	dao.registrarTransferencia(dto);      
    }
    
    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
    	dao.registrarChegadaEstoque(dto);
    }
}