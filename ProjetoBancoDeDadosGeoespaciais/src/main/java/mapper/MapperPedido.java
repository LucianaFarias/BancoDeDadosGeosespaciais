package mapper;

import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.ItemPedidoDTO;
import dto.LocalizacaoDTO;
import dto.PedidoDTO;
import model.Cliente;
import model.ItemPedido;
import model.Localizacao;
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
        
        LocalizacaoDTO origem = new LocalizacaoDTO();
        if(pedido.getOrigemDoPedido() != null) {
        	origem = mapperLocalizacao.toDTO(pedido.getOrigemDoPedido());
        }
        
        LocalizacaoDTO destino = new LocalizacaoDTO();
        if(pedido.getLocalDeEntrega() != null) {
        	destino = mapperLocalizacao.toDTO(pedido.getLocalDeEntrega());
        }
        
        ClienteDTO cliente = new ClienteDTO();
        if(pedido.getCliente() != null) {
        	MapperCliente mapperCliente = new MapperCliente();
        	cliente = mapperCliente.toDTO(pedido.getCliente());
        }

        return new PedidoDTO(pedido.getId(),
        		itensPedidoDTO,
        		destino,
        		origem,
        		MapperFilial.toDTO(pedido.getFilialResponsavel()),
        		pedido.getStatus(),
        		cliente);
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {

        List<ItemPedidoDTO> itensPedidoDTO = pedidoDTO.getItens();
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoDTO itemPedidoDTO : itensPedidoDTO) {
            itensPedido.add(mapperItemPedido.toEntity(itemPedidoDTO));
        }
        
        Localizacao origem = new Localizacao();
        if(pedidoDTO.getOrigemDoPedido() != null) {
        	origem = mapperLocalizacao.toEntity(pedidoDTO.getOrigemDoPedido());
        }
        
        Localizacao destino = new Localizacao();
        if(pedidoDTO.getLocalDeEntrega() != null) {
        	destino = mapperLocalizacao.toEntity(pedidoDTO.getLocalDeEntrega());
        }
        
        Cliente cliente = new Cliente();
        if(pedidoDTO.getCliente() != null) {
        	MapperCliente mapperCliente = new MapperCliente();
        	cliente = mapperCliente.toEntity(pedidoDTO.getCliente());
        }
        
        return new Pedido(pedidoDTO.getId(),
        		itensPedido,
        		destino,
        		origem,
        		MapperFilial.toEntity(pedidoDTO.getFilialResponsavel()),
        		cliente,
        		pedidoDTO.getStatus());
    }

	public MapperLocalizacao getMapperLocalizacao() {
		return mapperLocalizacao;
	}

	public void setMapperLocalizacao(MapperLocalizacao mapperLocalizacao) {
		this.mapperLocalizacao = mapperLocalizacao;
	}
}
