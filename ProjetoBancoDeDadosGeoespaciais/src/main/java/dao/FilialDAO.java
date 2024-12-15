package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.FilialDTO;
import exception.FilialInvalidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import mapper.MapperFilial;
import model.Filial;
 
public class FilialDAO implements IFilialDao {
	
    private EntityManagerFactory entityManagerFactory;

    public FilialDAO() {
        Conexao conexao = new Conexao();
        this.entityManagerFactory = conexao.criarConexao();
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
		if (dto == null || dto.getId() == 0) {
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
            TypedQuery<Filial> query = em.createQuery(
                    "SELECT f, ST_Distance(f.location, :filialOrigem) AS distance " +
                    "FROM Filial f WHERE f.id != :filialId " +
                    "ORDER BY distance", Filial.class);
            query.setParameter("filialId", filial.getId());
            query.setParameter("filialOrigem", filial.getEndereco().getPonto().toText());

            resultList = query.getResultList();
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
