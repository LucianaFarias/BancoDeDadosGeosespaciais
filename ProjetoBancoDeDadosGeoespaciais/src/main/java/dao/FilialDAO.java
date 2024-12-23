package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.FilialDTO;
import exception.FilialInvalidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mapper.MapperFilial;
import model.Filial;
 
public class FilialDAO implements IFilialDao {
	
    private EntityManagerFactory entityManagerFactory;

    public FilialDAO() {
        this.entityManagerFactory = Conexao.getInstancia().getFactory();
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

	// Retorna a lista ordenada pela distância, das mais próximas para as mais distantes
	@Override
	public List<FilialDTO> buscarFiliaisProximas(FilialDTO filial) throws RuntimeException{
		EntityManager em = entityManagerFactory.createEntityManager();

        List<Filial> resultList;
        try {
            em.getTransaction().begin();
            String query = 
            		"SELECT l2.* " +
            	    "FROM filial l1, filial l2 " +
            	    "WHERE l1.id = :id " +
            	    "AND l2.id != l1.id " +
            	    "ORDER BY function('ST_Distance', l1.endereco, l2.endereco)";
            Query nativeQuery = em.createNativeQuery(query, Filial.class);
            nativeQuery.setParameter("id", filial.getId());
            //query.setParameter("origem", filial.getEndereco().getPonto());
            //query.setParameter("destino", filial.getId());

            resultList = nativeQuery.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao listar filiais: " + e.getMessage(), e);
        } finally {
            em.close();
        }

        List<FilialDTO> filiais = new ArrayList<>();
        for (Filial filialEncontrada : resultList) {
            FilialDTO filialDTO = MapperFilial.toDTO(filialEncontrada);
            filiais.add(filialDTO);
        }

        return filiais;		
	}
}
