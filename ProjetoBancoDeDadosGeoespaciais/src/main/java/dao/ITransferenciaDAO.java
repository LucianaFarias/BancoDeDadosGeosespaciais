package dao;

import java.util.List;

import dto.FilialDTO;
import dto.TransferenciaDTO;

public interface ITransferenciaDAO {
	
	public void registrarTransferencia(TransferenciaDTO dto) throws Exception;
	public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception;
	public List<TransferenciaDTO> buscarTransferenciasPorFilial(FilialDTO dto) throws Exception;
}