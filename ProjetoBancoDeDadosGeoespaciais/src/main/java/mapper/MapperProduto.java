package mapper;

import dto.ProdutoDTO;
import model.Produto;

public class MapperProduto {
	
	public ProdutoDTO toDTO(Produto produto) {
		return new ProdutoDTO(produto.getId(), produto.getNome());
	}
	
	public Produto toEntity(ProdutoDTO produto) {
		return new Produto(produto.getId(), produto.getNome());
	}

}
