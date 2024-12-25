package controller;

import java.sql.SQLException;

import dao.ConexaoJDBC;
import dao.ConexaoJPA;

public class ProgramaController {
	
	//Fecha a EntityManagerFactory e encerra o programa
	public void encerrar() {
		try {
			ConexaoJDBC.getInstancia().fechar();
		} catch (SQLException e) {
			System.out.println("Erro ao acessar dados");
			e.printStackTrace();
		}
		ConexaoJPA.getInstancia().fechar();
		System.exit(0);
	}

}
