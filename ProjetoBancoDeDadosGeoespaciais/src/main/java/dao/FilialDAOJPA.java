package dao;

import java.util.List;
import java.util.stream.Collectors;

import dto.FilialDTO;
import exception.FilialInvalidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperFilial;
import model.Filial;
 
public class FilialDAOJPA implements IFilialDao {
	
    private EntityManagerFactory entityManagerFactory;

    public FilialDAOJPA() {
        this.entityManagerFactory = ConexaoJPA.getInstancia().getFactory();
    }
	
	@Override
	public void cadastrarFilial(FilialDTO dto) {
	    EntityManager em = entityManagerFactory.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        Filial filial = MapperFilial.toEntity(dto);

	        em.persist(filial);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw new RuntimeException();
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	}

	@Override
    public List<FilialDTO> listarFiliais() {
		EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Filial> query = em.createQuery("SELECT f FROM Filial f", Filial.class);
            List<Filial> filiais = query.getResultList();

            return filiais.stream()
                    .map(MapperFilial::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar filiais: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

	@Override
	public FilialDTO buscarFilialPorId(FilialDTO dto) throws FilialInvalidaException {
		if (dto == null) {
			throw new FilialInvalidaException();
		    }

		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Filial filial = em.find(Filial.class, dto.getId());
			if (filial != null) {
				return MapperFilial.toDTO(filial);
		        }
		    } catch (Exception e) {
		        throw new RuntimeException();
		    } finally {
		        em.close();
		    }

		    return null;
		}

	@Override
	public List<FilialDTO> buscarFiliaisProximas(FilialDTO filial) {
		return null;
	}

}
