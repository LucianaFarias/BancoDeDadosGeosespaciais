package mapper;

import dto.TransferenciaDTO;
import model.Transferencia;

public class MapperTransferencia {

    private MapperProduto mapperProduto = new MapperProduto();

    public TransferenciaDTO toDTO(Transferencia transferencia) {
        return new TransferenciaDTO(
            transferencia.getId(),
            MapperFilial.toDTO(transferencia.getOrigem()),
            MapperFilial.toDTO(transferencia.getDestino()),
            mapperProduto.toDTO(transferencia.getProduto()),
            transferencia.getQuantidade()
        );
    }

    public Transferencia toEntity(TransferenciaDTO dto) {
        Transferencia transferencia = new Transferencia();
        transferencia.setId(dto.getId());
        transferencia.setOrigem(MapperFilial.toEntity(dto.getOrigem()));
        transferencia.setDestino(MapperFilial.toEntity(dto.getDestino()));
        transferencia.setProduto(mapperProduto.toEntity(dto.getProduto()));
        transferencia.setQuantidade(dto.getQuantidade());
        return transferencia;
    }
}