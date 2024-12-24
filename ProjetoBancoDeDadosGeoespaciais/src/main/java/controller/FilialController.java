package controller;

import java.util.List;

import dao.FilialDAOJDBC;
import dao.IFilialDao;
import dto.FilialDTO;
import exception.FilialInvalidaException;

public class FilialController {
    private IFilialDao filialDAO;

    public FilialController() {
        this.filialDAO = new FilialDAOJDBC();
    }

    public void cadastrarFilial(FilialDTO filialDTO) {
        try {
            filialDAO.cadastrarFilial(filialDTO);
            System.out.println("Filial cadastrada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar filial: " + e.getMessage());
        }
    }

    public List<FilialDTO> listarFiliais() {
        try {
            return filialDAO.listarFiliais();
        } catch (Exception e) {
            System.err.println("Erro ao listar filiais: " + e.getMessage());
            return null;
        }
    }

    public FilialDTO buscarFilialPorId(FilialDTO filialDTO) {
        try {
            return filialDAO.buscarFilialPorId(filialDTO);
        } catch (FilialInvalidaException e) {
            System.err.println("Filial inválida: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar filial: " + e.getMessage());
            return null;
        }
    }
}
