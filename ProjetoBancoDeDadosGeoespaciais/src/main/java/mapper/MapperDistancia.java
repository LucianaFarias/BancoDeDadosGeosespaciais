package mapper;

import model.Distancia;
import dto.DistanciaDTO;

public class MapperDistancia {

    public DistanciaDTO toDTO(Distancia distancia) {
        if (distancia == null) {
            return null;
        }

        DistanciaDTO distanciaDTO = new DistanciaDTO();
        distanciaDTO.setFilial1(MapperFilial.toDTO(distancia.getFilial1()));
        distanciaDTO.setFilial2(MapperFilial.toDTO(distancia.getFilial2()));
        distanciaDTO.setDistancia(distancia.getDistancia());

        return distanciaDTO;
    }

    public Distancia toEntity(DistanciaDTO distanciaDTO) {
        if (distanciaDTO == null) {
            return null;
        }

        Distancia distancia = new Distancia();
        distancia.setFilial1(MapperFilial.toEntity(distanciaDTO.getFilial1()));
        distancia.setFilial2(MapperFilial.toEntity(distanciaDTO.getFilial2()));
        distancia.setDistancia(distanciaDTO.getDistancia());
        // Add other fields as needed

        return distancia;
    }
}