package mappers;

import dto.FilialDTO;
import model.Filial;

public class MapperFilial {

    private MapperLocalizacao mapperLocalizacao;

    public MapperFilial() {
        this.mapperLocalizacao = new MapperLocalizacao();
    }

    public FilialDTO toDTO(Filial filial) {
        FilialDTO dto = new FilialDTO();
        dto.setId(filial.getId());
        dto.setNome(filial.getNome());
        dto.setEndereco(mapperLocalizacao.toDTO(filial.getEndereco()));
        return dto;
    }

    public Filial toEntity(FilialDTO filial) {
        Filial entity = new Filial();
        entity.setNome(filial.getNome());
        entity.setEndereco(mapperLocalizacao.toEntity(filial.getEndereco()));
        return entity;
    }
}