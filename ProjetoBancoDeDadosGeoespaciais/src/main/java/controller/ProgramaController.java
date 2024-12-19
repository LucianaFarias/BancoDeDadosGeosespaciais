package controller;

import dao.Conexao;

public class ProgramaController {
	
	//Fecha a EntityManagerFactory e encerra o programa
	public void encerrar() {
		Conexao.getInstancia().fecharConexao();
		System.exit(0);
	}

}
