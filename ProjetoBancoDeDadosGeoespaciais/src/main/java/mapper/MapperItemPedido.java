package mapper;

import dto.ItemPedidoDTO;
import dto.ProdutoDTO;
import model.ItemPedido;
import model.Produto;

public class MapperItemPedido {
    private MapperProduto mapperProduto;

    public MapperItemPedido() {
        this.mapperProduto = new MapperProduto();
    }

    public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        Produto produto = itemPedido.getProduto();
        ProdutoDTO produtoDTO = mapperProduto.toDTO(produto);

        return new ItemPedidoDTO(itemPedido.getId(), produtoDTO, itemPedido.getQuantidade());
    }

    public ItemPedido toEntity(ItemPedidoDTO itemPedidoDTO) {
        ProdutoDTO produtoDTO = itemPedidoDTO.getProduto();
        Produto produto = mapperProduto.toEntity(produtoDTO);

        return new ItemPedido(itemPedidoDTO.getId(), produto, itemPedidoDTO.getQuantidade());
    }
}