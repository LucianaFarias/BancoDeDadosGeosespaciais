package mapper;

import model.Filial;
import model.Localizacao;
import dto.FilialDTO;
import dto.LocalizacaoDTO;

public class MapperFilial {

    public static FilialDTO toDTO(Filial filial) {
        if (filial == null) {
            return null;
        }

        FilialDTO dto = new FilialDTO();
        dto.setNome(filial.getNome());
        dto.setId(filial.getId());

        if (filial.getEndereco() != null) {
            LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO();
            localizacaoDTO.setEstado(filial.getEndereco().getEstado());
            localizacaoDTO.setCidade(filial.getEndereco().getCidade());
            localizacaoDTO.setLatitude(filial.getEndereco().getLatitude());
            localizacaoDTO.setLongitude(filial.getEndereco().getLongitude());
            dto.setEndereco(localizacaoDTO);
        }

        return dto;
    }

    public static Filial toEntity(FilialDTO dto) {
        if (dto == null) {
            return null;
        }

        Localizacao localizacao = new Localizacao();
        if (dto.getEndereco() != null) {
            localizacao.setEstado(dto.getEndereco().getEstado());
            localizacao.setCidade(dto.getEndereco().getCidade());
            localizacao.setLatitude(dto.getEndereco().getLatitude());
            localizacao.setLongitude(dto.getEndereco().getLongitude());
        }

        Filial filial = new Filial(dto.getId(), dto.getNome(), localizacao);
        return filial;
    }
}