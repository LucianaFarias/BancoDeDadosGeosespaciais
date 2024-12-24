package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ProdutoDTO;
import mapper.MapperEstoque;

public class EstoqueDAOJDBC implements IEstoqueDAO{
	
	private Connection connection;
    private MapperEstoque mapper;

    public EstoqueDAOJDBC(Connection connection) {
        this.connection = ConexaoJDBC.getInstancia().getConnection();
        this.mapper = new MapperEstoque();
    }
    
    @Override
    public List<EstoqueDTO> buscarEstoquesDaFilial(FilialDTO filial) throws SQLException {
        List<EstoqueDTO> estoques = new ArrayList<>();
        String sql = "SELECT * FROM estoque WHERE filial_id = ? AND quantidade > 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, filial.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EstoqueDTO estoque = mapper.toDTO(resultSet);
                estoques.add(estoque);
            }
        }

        return estoques;
    }
    
    @Override
    public List<EstoqueDTO> buscarEstoquesDoProduto(ProdutoDTO produto) throws SQLException {
        List<EstoqueDTO> estoques = new ArrayList<>();
        String sql = "SELECT * FROM estoque WHERE produto_id = ? AND quantidade > 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, produto.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EstoqueDTO estoque = mapper.toDTO(resultSet);
                estoques.add(estoque);
            }
        }

        return estoques;
    }
    
    @Override
    public void atualizarEstoque(EstoqueDTO estoque) throws SQLException {
        String sql = "UPDATE estoque SET quantidade = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, estoque.getQuantidade());
            statement.setInt(2, estoque.getId());
            statement.executeUpdate();
        }
    }
    
    @Override
    public List<EstoqueDTO> listarEstoques() throws SQLException {
        List<EstoqueDTO> estoques = new ArrayList<>();
        String sql = "SELECT * FROM estoque";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EstoqueDTO estoque = mapper.toDTO(resultSet);
                estoques.add(estoque);
            }
        }

        return estoques;
    }
}
