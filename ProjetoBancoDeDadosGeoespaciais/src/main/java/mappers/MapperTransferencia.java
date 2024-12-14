package mappers;

import dto.TransferenciaDTO;
import model.Transferencia;

public class MapperTransferencia {

    private MapperFilial mapperFilial = new MapperFilial();
    private MapperProduto mapperProduto = new MapperProduto();

    public TransferenciaDTO toDTO(Transferencia transferencia) {
        return new TransferenciaDTO(
            transferencia.getId(),
            mapperFilial.toDTO(transferencia.getOrigem()),
            mapperFilial.toDTO(transferencia.getDestino()),
            mapperProduto.toDTO(transferencia.getProduto()),
            transferencia.getQuantidade()
        );
    }

    public Transferencia toEntity(TransferenciaDTO dto) {
        Transferencia transferencia = new Transferencia();
        transferencia.setId(dto.getId());
        transferencia.setOrigem(mapperFilial.toEntity(dto.getOrigem()));
        transferencia.setDestino(mapperFilial.toEntity(dto.getDestino()));
        transferencia.setProduto(mapperProduto.toEntity(dto.getProduto()));
        transferencia.setQuantidade(dto.getQuantidade());
        return transferencia;
    }
}