package mapper;

import dto.LocalizacaoDTO;
import model.Localizacao;

public class MapperLocalizacao {

    public LocalizacaoDTO toDTO(Localizacao localizacao) {
    	LocalizacaoDTO dto = new LocalizacaoDTO();
    	if(localizacao != null) {
    		dto.setId(localizacao.getId());
    		dto.setCidade(localizacao.getCidade());
    		dto.setEstado(localizacao.getEstado());
    		dto.setLatitude(localizacao.getLatitude());
    		dto.setLongitude(localizacao.getLongitude());
    		dto.setPonto(localizacao.getPonto()); 		
    	}
        return dto;
    }

    public Localizacao toEntity(LocalizacaoDTO localizacao) {
        Localizacao entity = new Localizacao(
        		localizacao.getId(),
        		localizacao.getCidade(),
        		localizacao.getEstado(),
        		localizacao.getLatitude(),
        		localizacao.getLongitude());
        
        return entity;    
    }

}