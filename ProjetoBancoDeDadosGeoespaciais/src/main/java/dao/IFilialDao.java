package dao;

import java.util.List;

import dto.FilialDTO;
import exception.FilialInvalidaException;

public interface IFilialDao {
	public void cadastrarFilial(FilialDTO filial);
    public List<FilialDTO> listarFiliais();
    public FilialDTO buscarFilialPorId(FilialDTO filial)throws FilialInvalidaException;
    public List<FilialDTO> buscarFiliaisProximas(FilialDTO filial) throws RuntimeException;
}
