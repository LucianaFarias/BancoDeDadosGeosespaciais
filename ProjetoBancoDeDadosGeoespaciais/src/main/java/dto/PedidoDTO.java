package dto;

import java.util.List;

public class PedidoDTO {
    private int id;
    private List<ItemPedidoDTO> itens;
    private LocalizacaoDTO localDeEntrega;
    private LocalizacaoDTO origemDoPedido;

    public PedidoDTO(int id, List<ItemPedidoDTO> itens, LocalizacaoDTO localDeEntrega,
			LocalizacaoDTO origemDoPedido) {
		this.id = id;
		this.itens = itens;
		this.localDeEntrega = localDeEntrega;
		this.origemDoPedido = origemDoPedido;
	}

	public PedidoDTO() {
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemPedidoDTO> getitens() {
        return itens;
    }

    public void setitens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

	public LocalizacaoDTO getLocalDeEntrega() {
		return localDeEntrega;
	}

	public void setLocalDeEntrega(LocalizacaoDTO localDeEntrega) {
		this.localDeEntrega = localDeEntrega;
	}

	public LocalizacaoDTO getOrigemDoPedido() {
		return origemDoPedido;
	}

	public void setOrigemDoPedido(LocalizacaoDTO origemDoPedido) {
		this.origemDoPedido = origemDoPedido;
	}
}
