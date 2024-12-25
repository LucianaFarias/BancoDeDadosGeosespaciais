package dto;

import java.util.List;

import model.StatusPedido;

public class PedidoDTO {
    private int id;
    private List<ItemPedidoDTO> itens;
    private LocalizacaoDTO localDeEntrega;
    private LocalizacaoDTO origemDoPedido;
    private FilialDTO filialResponsavel;
	private StatusPedido status;
	private ClienteDTO cliente;

    public PedidoDTO(int id, List<ItemPedidoDTO> itens, LocalizacaoDTO localDeEntrega,
			LocalizacaoDTO origemDoPedido, FilialDTO filialResponsavel, StatusPedido status, ClienteDTO cliente) {
		this.id = id;
		this.itens = itens;
		this.localDeEntrega = localDeEntrega;
		this.origemDoPedido = origemDoPedido;
		this.setFilialResponsavel(filialResponsavel);
		this.status = status;
		this.cliente = cliente;
	}

	public PedidoDTO() {
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
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

	public FilialDTO getFilialResponsavel() {
		return filialResponsavel;
	}

	public void setFilialResponsavel(FilialDTO filialResponsavel) {
		this.filialResponsavel = filialResponsavel;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
}
