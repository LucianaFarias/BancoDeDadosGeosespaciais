package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexao {
	
	private static Conexao INSTANCIA;
    private final EntityManagerFactory factory;

    private Conexao() {
    	this.factory = Persistence.createEntityManagerFactory("spatialite-test");
    }
    
    public static Conexao getInstancia() {
    	if(INSTANCIA == null) {
    		INSTANCIA = new Conexao();
    	}
        return INSTANCIA;
    }
    
	public void fecharConexao() {
		if (factory.isOpen()){
			factory.close();
		}	
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

}
