package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexao {
	
	public EntityManagerFactory criarConexao() {
	    EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("spatialite-test");
		return entityFactory;
	}
	public void fecharConexao(EntityManagerFactory entityFactory) {
		if (entityFactory.isOpen()){
			entityFactory.close();
		}	
	}

}
