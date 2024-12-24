package controller;

import java.util.List;

import dao.ITransferenciaDAO;
import dao.TransferenciaDAOJPA;
import dto.FilialDTO;
import dto.TransferenciaDTO;

public class TransferenciaController {

    private ITransferenciaDAO dao = TransferenciaDAOJPA.getInstance();  // Obtendo a instância do DAO via Singleton

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
        dao.registrarTransferencia(dto);
    }

    public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception {
        dao.registrarChegadaEstoque(dto);
    }

    public void cancelarTransferencia(TransferenciaDTO dto) throws Exception {
        dao.cancelarTransferencia(dto);
    }
    
    public void excluirTransferencia(TransferenciaDTO dto) throws Exception {
        dao.excluirTransferencia(dto);
    }

    public List<TransferenciaDTO> listarTransferenciasPorFilial(FilialDTO filial) throws Exception {
        return dao.buscarTransferenciasPorFilial(filial);
    }

    public void gerarRelatorioTransferenciasPorOrigem(FilialDTO origem) throws Exception {
        List<TransferenciaDTO> transferencias = dao.listarTransferenciasPorOrigem(origem);
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
