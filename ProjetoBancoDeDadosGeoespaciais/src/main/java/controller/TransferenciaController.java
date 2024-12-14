package controller;

import java.util.List;
import dao.TransferenciaDAO;
import dto.TransferenciaDTO;
import dto.FilialDTO;

public class TransferenciaController {

    private TransferenciaDAO dao;

    public List<TransferenciaDTO> listarTransferenciasPorFilial(FilialDTO dto) throws Exception {
        return dao.buscarTransferenciasPorFilial(dto);
        		      		
    }

    public void registrarTransferencia(TransferenciaDTO dto) throws Exception {
        dao.registrarTransferencia(dto);
    }
}