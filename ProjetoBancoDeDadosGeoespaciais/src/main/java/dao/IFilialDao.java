package dao;

import java.util.List;

import dto.FilialDTO;

public interface IFilialDAO {
	public void cadastrarFilial(FilialDTO filial);
    public List<FilialDTO> listarFiliais();
    public FilialDTO buscarFilialPorId(FilialDTO filial);
}
