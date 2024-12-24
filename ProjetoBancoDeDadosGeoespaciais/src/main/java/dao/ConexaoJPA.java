package dao;

import java.sql.SQLException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexaoJPA {
	
	private static ConexaoJPA INSTANCIA;
    private EntityManagerFactory factory;

    private ConexaoJPA() {
    	this.factory = Persistence.createEntityManagerFactory("loja");
    }
    
    public static ConexaoJPA getInstancia() {
    	if(INSTANCIA == null) {
    		INSTANCIA = new ConexaoJPA();
    	}
        return INSTANCIA;
    }
    
	public void fechar() {
		if (factory.isOpen()){
			factory.close();
		}	
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public ConexaoJPA abrir() {
		try {
			if(!ConexaoJDBC.getInstancia().getConnection().isClosed()) {
				ConexaoJDBC.getInstancia().fechar();
			}
		} catch (SQLException e) {
			System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
		}
		return getInstancia();
	}

}
