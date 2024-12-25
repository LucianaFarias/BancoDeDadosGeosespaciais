package dao;

import java.util.List;

import dto.FilialDTO;
import dto.TransferenciaDTO;

public interface ITransferenciaDAO {
	
	public void registrarTransferencia(TransferenciaDTO dto) throws Exception;
	public void registrarChegadaEstoque(TransferenciaDTO dto) throws Exception;
	public void cancelarTransferencia(TransferenciaDTO dto) throws Exception;
	public void excluirTransferencia(TransferenciaDTO dto) throws Exception;
	public List<TransferenciaDTO> buscarTransferenciasPorFilial(FilialDTO dto) throws Exception;
	public List<TransferenciaDTO> listarTransferenciasPorOrigem(FilialDTO dto) throws Exception;
    public TransferenciaDTO buscarTransferenciaPorId(TransferenciaDTO dto) throws Exception;

}