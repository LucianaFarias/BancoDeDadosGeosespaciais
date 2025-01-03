package controller;

import java.util.ArrayList;
import java.util.List;

import dao.EstoqueDAOJPA;
import dao.FilialDAOJDBC;
import dao.IEstoqueDAO;
import dao.IFilialDao;
import dao.IPedidoDAO;
import dao.PedidoDAOJPA;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ItemPedidoDTO;
import dto.PedidoDTO;
import dto.TransferenciaDTO;
import exception.EstoqueInsuficienteException;
import mapper.MapperEstoque;
import mapper.MapperPedido;
import model.Estoque;
import model.Pedido;
import model.StatusPedido;

public class EstoqueController {
	
	private IEstoqueDAO estoqueDAO;
	private IFilialDao filialDAO;
	private MapperEstoque mapperEstoque;
	private IPedidoDAO pedidoDAO;
	
	public EstoqueController() {
		this.estoqueDAO = new EstoqueDAOJPA();
		this.filialDAO = new FilialDAOJDBC();
		this.mapperEstoque = new MapperEstoque();
		this.setPedidoDAO(new PedidoDAOJPA());
	}
	
	public void transferir(TransferenciaDTO transferencia) throws Exception {
		List<EstoqueDTO> estoques = estoqueDAO.buscarEstoquesDoProduto(transferencia.getProduto());
		EstoqueDTO estoqueOrigem = filtrarEstoquesDaFilial(estoques, transferencia.getOrigem()).get(0);
		EstoqueDTO estoqueDestino = filtrarEstoquesDaFilial(estoques, transferencia.getDestino()).get(0);
		
		Estoque entityOrigem = mapperEstoque.toEntity(estoqueOrigem);
		entityOrigem.remover(transferencia.getQuantidade());
		atualizarEstoque(mapperEstoque.toDTO(entityOrigem));
		
		estoqueDestino.setQuantidade(estoqueDestino.getQuantidade() + transferencia.getQuantidade());
		atualizarEstoque(estoqueDestino);
	}
	
	public void atenderPedido(PedidoDTO pedido) throws Exception {
		if(pedido.getStatus() != StatusPedido.CONCLUIDO) {
			
			for(ItemPedidoDTO item: pedido.getItens()) {
				List<EstoqueDTO> estoques = estoqueDAO.buscarEstoquesDoProduto(item.getProduto());
				estoques = filtrarEstoquesDaFilial(estoques, pedido.getFilialResponsavel());
				Estoque estoqueEntity = mapperEstoque.toEntity(estoques.get(0));
				estoqueEntity.remover(item.getQuantidade());
				atualizarEstoque(mapperEstoque.toDTO(estoqueEntity));
			}
		}
		
	}

	public List<TransferenciaDTO> necessitaTransferenciaDeEstoque(FilialDTO filial, PedidoDTO pedido) throws Exception {
		List<TransferenciaDTO> transferenciasNecessarias = new ArrayList<>();
		
		List<EstoqueDTO> estoquesQueFaltam = verificarSeFaltaEstoque(filial, pedido);
		if(estoquesQueFaltam.size()>0) {
			List<FilialDTO> filiaisProximasDaInicial = buscarFiliaisMaisProximas(filial);
			
			for(FilialDTO filialProxima: filiaisProximasDaInicial) {
				if(filialProxima.getId() != filial.getId()) {
					List<EstoqueDTO> estoquesNaFilial = buscarEstoquesDisponiveis(estoquesQueFaltam, filialProxima);
					if(estoquesNaFilial.size()>0) {
						for (EstoqueDTO estoque : estoquesNaFilial) {
							TransferenciaDTO transferencia = mapperEstoque.toEntity(estoque).criarTransferencia(filial, estoque.getQuantidade());
							transferenciasNecessarias.add(transferencia);
							try {
								estoquesQueFaltam = atualizarQuantidadeEstoques(estoquesQueFaltam, transferenciasNecessarias);
							} catch (EstoqueInsuficienteException e) {
								System.out.print("x");
							}
						}
						if(estoquesQueFaltam.isEmpty()) {
							return transferenciasNecessarias;
						}
					}	
				}

			}
			if(!estoquesQueFaltam.isEmpty()) {
				pedido.setStatus(StatusPedido.ESTOQUE_INDISPONIVEL);
				pedidoDAO.atualizar(pedido);
				throw new EstoqueInsuficienteException("Não há estoque suficiente nas filiais");
			}
			
		}
		return transferenciasNecessarias;
	}
	
	// Se o estoque for suficiente, a lista será vazia
	public List<EstoqueDTO> verificarSeFaltaEstoque(FilialDTO filial, PedidoDTO pedido) throws Exception {
		
		List<EstoqueDTO> estoques;
		estoques = estoqueDAO.buscarEstoquesDaFilial(filial);
		MapperPedido mapper = new MapperPedido();
		Pedido pedidoEntidade = mapper.toEntity(pedido);
		return pedidoEntidade.faltaEstoque(estoques);
		
	}
	
	public List<EstoqueDTO> buscarEstoquesDisponiveis(List<EstoqueDTO> itensNecessarios, FilialDTO filial) throws Exception {
		List<EstoqueDTO> estoquesEncontrados = new ArrayList<>();
		for(EstoqueDTO estoque: itensNecessarios) {
			estoquesEncontrados.addAll(estoqueDAO.buscarEstoquesDoProduto(estoque.getProduto()));
		}

		estoquesEncontrados = filtrarEstoquesDaFilial(estoquesEncontrados, filial);
		estoquesEncontrados = verificarQuantidadeParaTransferir(itensNecessarios, estoquesEncontrados);
		return estoquesEncontrados;

		
	}

	public List<EstoqueDTO> verificarQuantidadeParaTransferir(List<EstoqueDTO> estoquesNecessarios, List<EstoqueDTO> estoquesDisponiveis) {
		List<EstoqueDTO> estoquesParaTransferir = estoquesDisponiveis;
        for(EstoqueDTO estoqueNecessario: estoquesNecessarios) {
            for(EstoqueDTO estoqueParaTransferir: estoquesParaTransferir) {
                if(estoqueParaTransferir.getQuantidade() >= estoqueNecessario.getQuantidade()){
					estoqueParaTransferir.setQuantidade(estoqueNecessario.getQuantidade());
				}
			}
		}
        return estoquesParaTransferir;
	}
	
	
	public List<EstoqueDTO> filtrarEstoquesDaFilial(List<EstoqueDTO> estoques, FilialDTO filial) {
		
		List<EstoqueDTO> estoquesDaFilial = new ArrayList<>();
		for(EstoqueDTO estoque: estoques) {
			if(estoque.getFilial().getId() == filial.getId()) {
				estoquesDaFilial.add(estoque);
			}
		}
		
		return estoquesDaFilial;
	}	
	
	public List<EstoqueDTO> atualizarQuantidadeEstoques(List<EstoqueDTO> estoques, List<TransferenciaDTO> transferencias) throws EstoqueInsuficienteException {
		for(int n = 0; n<estoques.size(); n++) {
			Estoque estoqueEntity = mapperEstoque.toEntity(estoques.get(n));
			try {
				estoqueEntity.atualizarEstoque(transferencias);
			} catch (EstoqueInsuficienteException e) {
				estoqueEntity.remover(estoques.get(n).getQuantidade());
			}
			estoques.set(n, mapperEstoque.toDTO(estoqueEntity));
			if(estoques.get(n).getQuantidade() == 0) {
				estoques.remove(n);
			}
		}
		return estoques;
		
	}
	
	public List<FilialDTO> buscarFiliaisMaisProximas(FilialDTO filial) throws Exception{
		
		List<FilialDTO> filiaisProximasDaInicial = filialDAO.buscarFiliaisProximas(filial);
		return filiaisProximasDaInicial;
	}
	
	public void atualizarEstoque(EstoqueDTO estoque) throws Exception {
		estoqueDAO.atualizarEstoque(estoque);
	}
	
	public List<EstoqueDTO> listarEstoques() throws Exception{
		return estoqueDAO.listarEstoques();
	}

	public IEstoqueDAO getEstoqueDAO() {
		return estoqueDAO;
	}

	public void setEstoqueDAO(IEstoqueDAO estoqueDAO) {
		this.estoqueDAO = estoqueDAO;
	}

	public IFilialDao getFilialDAO() {
		return filialDAO;
	}

	public void setFilialDAO(IFilialDao filialDAO) {
		this.filialDAO = filialDAO;
	}

	public MapperEstoque getMapperEstoque() {
		return mapperEstoque;
	}

	public void setMapperEstoque(MapperEstoque mapperEstoque) {
		this.mapperEstoque = mapperEstoque;
	}

	public IPedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(IPedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

}
