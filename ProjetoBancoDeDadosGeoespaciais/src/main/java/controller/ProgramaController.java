package controller;

import dao.ConexaoJDBC;
import dao.ConexaoJPA;

public class ProgramaController {
	
	//Fecha a EntityManagerFactory e encerra o programa
	public void encerrar() {
		ConexaoJDBC.getInstancia().fechar();
		ConexaoJPA.getInstancia().fechar();
		System.exit(0);
	}

}
