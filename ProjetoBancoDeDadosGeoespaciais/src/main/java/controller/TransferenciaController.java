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
import mapper.MapperTransferencia;
import model.Transferencia;

public class TransferenciaController {

    private TransferenciaDAO dao;

    public List<TransferenciaDTO> listarTransferenciasPorFilial(TransferenciaDTO dto) throws Exception {
        return dao.buscarTransferenciasPorFilial(dto);
    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
        dao.registrarTransferencia(dto);
    }

    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
        dao.registrarChegadaEstoque(dto);
    }

    public void gerarRelatorioTransferenciasPorOrigem(TransferenciaDTO dto) throws Exception {
        List<TransferenciaDTO> transferencias = dao.listarTransferenciasPorOrigem(dto);
        System.out.println("Relatório de Movimentações de Estoque por Origem:");
        for (TransferenciaDTO transferencia : transferencias) {
            System.out.println("ID: " + transferencia.getId() +
                               ", Produto: " + transferencia.getProduto().getNome() +
                               ", Origem: " + transferencia.getOrigem().getNome() +
                               ", Destino: " + transferencia.getDestino().getNome() +
                               ", Quantidade: " + transferencia.getQuantidade());
        }
    }
}