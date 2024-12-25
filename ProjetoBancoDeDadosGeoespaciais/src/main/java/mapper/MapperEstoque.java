package mapper;

import dto.EstoqueDTO;
import model.Estoque;

public class MapperEstoque {

    private MapperProduto mapperProduto;

    public MapperEstoque() {
        this.mapperProduto = new MapperProduto();
    }

    public EstoqueDTO toDTO(Estoque estoque) {
        EstoqueDTO dto = new EstoqueDTO();
        dto.setId(estoque.getId());
        dto.setQuantidade(estoque.getQuantidade());
        dto.setProduto(mapperProduto.toDTO(estoque.getProduto()));
        dto.setFilial(MapperFilial.toDTO(estoque.getFilial()));
        return dto;
    }

    public Estoque toEntity(EstoqueDTO estoque) {
        Estoque entity = new Estoque();
        entity.setId(estoque.getId());
        entity.setQuantidade(estoque.getQuantidade());
        entity.setProduto(mapperProduto.toEntity(estoque.getProduto()));
        entity.setFilial(MapperFilial.toEntity(estoque.getFilial()));
        return entity;
    }
}