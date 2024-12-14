package mappers;

import java.util.ArrayList;
import java.util.List;

import dto.ItemPedidoDTO;
import dto.PedidoDTO;
import model.ItemPedido;
import model.Pedido;

public class MapperPedido {
    private MapperItemPedido mapperItemPedido;
    private MapperLocalizacao mapperLocalizacao;

    public MapperPedido() {
		this.mapperItemPedido = new MapperItemPedido();
		this.mapperLocalizacao = new MapperLocalizacao();
	}

	public PedidoDTO toDTO(Pedido pedido) {

        List<ItemPedido> itensPedido = pedido.getItens();
        List<ItemPedidoDTO> itensPedidoDTO = new ArrayList<>();
        for (ItemPedido itemPedido : itensPedido) {
            itensPedidoDTO.add(mapperItemPedido.toDTO(itemPedido));
        }

        return new PedidoDTO(pedido.getId(),
        		itensPedidoDTO,
        		mapperLocalizacao.toDTO(pedido.getLocalDeEntrega()),
        		mapperLocalizacao.toDTO(pedido.getOrigemDoPedido()));
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {

        List<ItemPedidoDTO> itensPedidoDTO = pedidoDTO.getItensPedido();
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoDTO itemPedidoDTO : itensPedidoDTO) {
            itensPedido.add(mapperItemPedido.toEntity(itemPedidoDTO));
        }
        
        return new Pedido(pedidoDTO.getId(),
        		itensPedido,
        		mapperLocalizacao.toEntity(pedidoDTO.getLocalDeEntrega()),
        		mapperLocalizacao.toEntity(pedidoDTO.getOrigemDoPedido()));
    }

	public MapperLocalizacao getMapperLocalizacao() {
		return mapperLocalizacao;
	}

	public void setMapperLocalizacao(MapperLocalizacao mapperLocalizacao) {
		this.mapperLocalizacao = mapperLocalizacao;
	}
}
