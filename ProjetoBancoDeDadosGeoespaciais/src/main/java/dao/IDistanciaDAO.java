package dao;

import java.util.List;

import dto.DistanciaDTO;
import dto.FilialDTO;

public interface IDistanciaDAO {
	
	public List<DistanciaDTO> buscarDistanciasDaFilial(FilialDTO filial) throws Exception;

}
