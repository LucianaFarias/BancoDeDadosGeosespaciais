package main;

import java.util.List;

import controller.FilialController;
import controller.ProgramaController;
import dao.FilialDAOJDBC;
import dto.FilialDTO;

public class Main {
    public static void main(String[] args) {
    	
    	try {
    	//ProdutoDTO p = new ProdutoDTO(1, "Escova");
    	
    	
    	FilialDTO f = new FilialDTO();
    	f.setId(14);
    	FilialDAOJDBC dao = new FilialDAOJDBC();
    	List<FilialDTO> fs = dao.buscarFiliaisProximas(f);
    	for(FilialDTO filial: fs) {
    		System.out.println(filial.getId()+filial.getNome());
    	}
    	
    	//EstoqueDTO e = new EstoqueDTO();
    	//e.setFilial(f);
    	//e.setProduto(p);
    	//e.setQuantidade(10);
     
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		ProgramaController pc = new ProgramaController();
    		pc.encerrar();
    	}
    }
}
