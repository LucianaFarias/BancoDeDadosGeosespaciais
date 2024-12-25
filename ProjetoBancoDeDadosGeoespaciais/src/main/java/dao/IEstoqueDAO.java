package dao;

import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;

public interface IEstoqueDAO {
	
	public List<EstoqueDTO> buscarEstoquesDaFilial(FilialDTO filial) throws Exception;
	public List<EstoqueDTO> buscarEstoquesDoProduto(ProdutoDTO produto) throws Exception;
	public List<EstoqueDTO> listarEstoques() throws Exception;
	public void atualizarEstoque(EstoqueDTO estoque) throws Exception;

}
