package mappers;

import dto.EstoqueDTO;
import model.Estoque;

public class MapperEstoque {

    private MapperProduto mapperProduto;
	private MapperFilial mapperFilial;

    public MapperEstoque() {
        this.mapperProduto = new MapperProduto();
        this.mapperFilial = new MapperFilial();
    }

    public EstoqueDTO toDTO(Estoque estoque) {
        EstoqueDTO dto = new EstoqueDTO();
        dto.setQuantidade(estoque.getQuantidade());
        dto.setProduto(mapperProduto.toDTO(estoque.getProduto()));
        dto.setFilial(mapperFilial.toDTO(estoque.getFilial()));
        return dto;
    }

    public Estoque toEntity(EstoqueDTO estoque) {
        Estoque entity = new Estoque();
        entity.setQuantidade(estoque.getQuantidade());
        entity.setProduto(mapperProduto.toEntity(estoque.getProduto()));
        entity.setFilial(mapperFilial.toEntity(estoque.getFilial()));
        return entity;
    }
}