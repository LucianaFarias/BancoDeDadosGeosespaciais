package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DistanciaDAO;
import dao.EstoqueDAO;
import dao.FilialDAO;
import dao.IDistanciaDAO;
import dao.IEstoqueDAO;
import dao.IFilialDao;
import dto.DistanciaDTO;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.PedidoDTO;
import dto.TransferenciaDTO;
import exception.EstoqueInsuficienteException;
import mapper.MapperEstoque;
import mapper.MapperPedido;
import model.Estoque;
import model.Pedido;

public class EstoqueController {
	
	private IEstoqueDAO estoqueDAO;
	private IFilialDao filialDAO;
	private IDistanciaDAO distanciaDAO;
	private MapperEstoque mapperEstoque;
	
	public EstoqueController() {
		this.estoqueDAO = new EstoqueDAO();
		this.filialDAO = new FilialDAO();
		this.setDistanciaDAO(new DistanciaDAO());
		this.mapperEstoque = new MapperEstoque();
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
		
		List<DistanciaDTO> distancias = distanciaDAO.buscarDistanciasDaFilial(filial);
		
		List<FilialDTO> filiaisProximasDaInicial = new ArrayList<>();
		for(DistanciaDTO distancia: distancias) {
			
			if(distancia.getFilial1().getId() == filial.getId()) { 
				
				filiaisProximasDaInicial.add(distancia.getFilial2());
				
			}else if(distancia.getFilial2().getId() == filial.getId()) {
				
				filiaisProximasDaInicial.add(distancia.getFilial1());

			}
		}
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

	public IDistanciaDAO getDistanciaDAO() {
		return distanciaDAO;
	}

	public void setDistanciaDAO(IDistanciaDAO distanciaDAO) {
		this.distanciaDAO = distanciaDAO;
	}
}
