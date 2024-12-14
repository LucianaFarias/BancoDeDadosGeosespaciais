package mappers;

import dto.LocalizacaoDTO;
import model.Localizacao;

public class MapperLocalizacao {

    public LocalizacaoDTO toDTO(Localizacao localizacao) {
    	LocalizacaoDTO dto = new LocalizacaoDTO();
        dto.setId(localizacao.getId());
        dto.setCidade(localizacao.getCidade());
        dto.setEstado(localizacao.getEstado());
        dto.setLatitude(localizacao.getLatitude());
        dto.setLongitude(localizacao.getLongitude());
        dto.setPonto(localizacao.getPonto());
        return dto;
    }

    public Localizacao toEntity(LocalizacaoDTO localizacao) {
        Localizacao entity = new Localizacao();
        entity.setId(localizacao.getId());
        entity.setCidade(localizacao.getCidade());
        entity.setEstado(localizacao.getEstado());
        entity.setLatitude(localizacao.getLatitude());
        entity.setLongitude(localizacao.getLongitude());
        entity.setPonto(localizacao.getPonto());
        return entity;    
    }

}